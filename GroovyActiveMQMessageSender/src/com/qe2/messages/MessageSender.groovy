package com.qe2.messages

import groovy.xml.MarkupBuilder
import java.sql.Connection
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.util.logging.Logger
import javax.xml.datatype.DatatypeConfigurationException
import javax.xml.datatype.DatatypeFactory
import javax.xml.datatype.XMLGregorianCalendar
import com.qe2.utilities.DBDetails
/*
 * NK 21072014 
 * Demo for sending activeMQ messaging 
 * Constructing xml using groovy MarkupBuilder.
 * Called from MessageSenderMain
 */
class MessageSender {
	
	private static final Logger LOG = Logger.getLogger(MessageSender.class.getName());	
	    
	//Sending activeMQ xml message to the QUEUE - CROSSING.HO.PERFORMANCE
	private static void sendMessage(){
		Connection con = null;
		DBDetails dbDetails = new DBDetails();
		con = dbDetails.getDBConnection();
		ResultSet rs = getResultSet(dbDetails.ACTIVITY_NAME, dbDetails, con);
		String xmlStr = getXMLForQ(rs,dbDetails.ACTIVITY_NAME);
		con.close();
		ActiveMQMessagePoster activeMQMessagePoster = new ActiveMQMessagePoster();
		activeMQMessagePoster.post(xmlStr,"CROSSING.HO.PERFORMANCE");
	}

    /**
     * Get xml for ActiveMQ
     * @param rs
     * @param queryName
     * @return
     */
	private static String getXMLForQ(ResultSet rs, String queryName){
		def xmlResponseStr = new StringWriter();
		xmlResponseStr.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		MarkupBuilder xml = new MarkupBuilder(xmlResponseStr);
		//uid, gate_number, vehicle_type, direction, vrn, crossing_datetime
		xml.'QE2-CROSSING-DATA'(){
			'report'('type':queryName){
				while(rs.next()){
					'activity'('uid':rs.getString("uid"),'gate_number':rs.getString("gate_number"),'vehicle_type':rs.getString("vehicle_type"),'direction':rs.getString("direction"),'vrn':rs.getString("vrn"),'crossing_datetime':gregDate(rs.getTimestamp("crossing_datetime")))
				}
			}
		}
		return xmlResponseStr.toString();
	}

    /**
     * get resultset for the the query
     */
	private static ResultSet getResultSet(String queryName, DBDetails dbDetails, Connection con) throws SQLException{
		LOG.info(queryName+"   query start");
		String sqlQuery = dbDetails.getSqlQuery(queryName);
		ResultSet rs = null;
		try {
			Statement stmt;
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			return rs;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		LOG.info(queryName+"   query end");
		return rs;
	}
	
    /**
     * To get xml format date [gregorian calendar date]
     * @param timestamp
     * @return
     * @throws DatatypeConfigurationException
     */
	private static XMLGregorianCalendar gregDate(Date timestamp) throws DatatypeConfigurationException {
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(timestamp);
		XMLGregorianCalendar gregDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		return gregDate;
	}
}
