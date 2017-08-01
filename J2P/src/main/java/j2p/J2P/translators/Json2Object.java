package j2p.J2P.translators;


import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import j2p.J2P.objects.BoardObject;
import j2p.J2P.objects.CustomFieldObject;
import j2p.J2P.objects.IssueObject;
import j2p.J2P.objects.PriorityObject;
import j2p.J2P.objects.SprintObject;
import j2p.J2P.objects.TaskObject;

public class Json2Object {
	
	public List<IssueObject> jsonToIssue(StringReader object, String customField, SprintObject sprint) {
		List<IssueObject> issueList = new ArrayList<IssueObject>();
		
		JsonReader reader = Json.createReader(object);
		JsonArray array   = reader.readObject().getJsonArray("issues");
		IssueObject io    = null;
		
		for(int i=0; i<array.size();i++) {
			JsonObject obj = array.getJsonObject(i);
			// String id, String key, String name, boolean subtask, Object timeSpent, String user,
			// String displayName, int points, String summary, String status, int priority, String priorityName
			try{
				if(obj.getJsonObject("fields").get("asignee") != null &&
						obj.getJsonObject("fields").get("customfield_" + customField).toString() != "null"){
					io = new IssueObject(obj.getString("id"),obj.getString("key"),
							obj.getJsonObject("fields").getJsonObject("issuetype").getString("name"),
							obj.getJsonObject("fields").getJsonObject("issuetype").getBoolean("subtask"),
							obj.getJsonObject("fields").get("timespent").toString(),
							obj.getJsonObject("fields").getJsonObject("assignee").getString("name"),
							obj.getJsonObject("fields").getJsonObject("assignee").getString("displayName"),
							obj.getJsonObject("fields").getJsonObject("reporter").getString("displayName"),
							Double.parseDouble(obj.getJsonObject("fields").get("customfield_" + customField).toString()), // points
							obj.getJsonObject("fields").getString("summary"),
							obj.getJsonObject("fields").getJsonObject("status").getString("name"),
							Integer.parseInt(obj.getJsonObject("fields").getJsonObject("priority").getString("id")),
							obj.getJsonObject("fields").getJsonObject("priority").getString("name"),
							obj.getJsonObject("fields").getString("updated"));
				} else if (obj.getJsonObject("fields").get("asignee") == null &&
						obj.getJsonObject("fields").get("customfield_" + customField).toString() != "null") {
					io = new IssueObject(obj.getString("id"),obj.getString("key"),
							obj.getJsonObject("fields").getJsonObject("issuetype").getString("name"),
							obj.getJsonObject("fields").getJsonObject("issuetype").getBoolean("subtask"),
							obj.getJsonObject("fields").get("timespent").toString(),
							"No assignee",
							"No assignee",
							obj.getJsonObject("fields").getJsonObject("reporter").getString("displayName"),
							Double.parseDouble(obj.getJsonObject("fields").get("customfield_" + customField).toString()), // points
							obj.getJsonObject("fields").getString("summary"),
							obj.getJsonObject("fields").getJsonObject("status").getString("name"),
							Integer.parseInt(obj.getJsonObject("fields").getJsonObject("priority").getString("id")),
							obj.getJsonObject("fields").getJsonObject("priority").getString("name"),
							obj.getJsonObject("fields").getString("updated"));
				} else if (obj.getJsonObject("fields").get("asignee") != null &&
						obj.getJsonObject("fields").get("customfield_" + customField).toString() == "null") {
					io = new IssueObject(obj.getString("id"),obj.getString("key"),
							obj.getJsonObject("fields").getJsonObject("issuetype").getString("name"),
							obj.getJsonObject("fields").getJsonObject("issuetype").getBoolean("subtask"),
							obj.getJsonObject("fields").get("timespent").toString(),
							obj.getJsonObject("fields").getJsonObject("assignee").getString("name"),
							obj.getJsonObject("fields").getJsonObject("assignee").getString("displayName"),
							obj.getJsonObject("fields").getJsonObject("reporter").getString("displayName"),
							0, // points
							obj.getJsonObject("fields").getString("summary"),
							obj.getJsonObject("fields").getJsonObject("status").getString("name"),
							Integer.parseInt(obj.getJsonObject("fields").getJsonObject("priority").getString("id")),
							obj.getJsonObject("fields").getJsonObject("priority").getString("name"),
							obj.getJsonObject("fields").getString("updated"));
				} else {
					io = new IssueObject(obj.getString("id"),obj.getString("key"),
							obj.getJsonObject("fields").getJsonObject("issuetype").getString("name"),
							obj.getJsonObject("fields").getJsonObject("issuetype").getBoolean("subtask"),
							obj.getJsonObject("fields").get("timespent").toString(),
							"No assignee",
							"No assignee",
							obj.getJsonObject("fields").getJsonObject("reporter").getString("displayName"),
							0, // points
							obj.getJsonObject("fields").getString("summary"),
							obj.getJsonObject("fields").getJsonObject("status").getString("name"),
							Integer.parseInt(obj.getJsonObject("fields").getJsonObject("priority").getString("id")),
							obj.getJsonObject("fields").getJsonObject("priority").getString("name"),
							obj.getJsonObject("fields").getString("updated"));
				}
			} catch(NullPointerException e) {
				e.printStackTrace();
			}
			io.setSprintObject(sprint);
			issueList.add(io);
		}
		
		reader.close();
		return issueList;
	}
	
	public List<SprintObject> jsonToSprintObj(StringReader object, BoardObject bobj){
		List<SprintObject> sprintList = new ArrayList<SprintObject>();
		
		JsonReader reader = Json.createReader(object);
		JsonArray array   = reader.readObject().getJsonArray("values");
		SprintObject so   = null;
		
		for(int i=0; i<array.size();i++) {
			JsonObject obj = array.getJsonObject(i);
			//String name, String state, int id, String startDate, String endDate, String completeDate
			try{
				so = new SprintObject(obj.getString("name"), obj.getString("state"), obj.getInt("id"), obj.getString("startDate"),
						obj.getString("endDate"), obj.getString("completeDate"));
			} catch(NullPointerException e) {
				so = new SprintObject(obj.getString("name"), obj.getString("state"), obj.getInt("id"));
			}
			so.setBoardObject(bobj);
			sprintList.add(so);
		}
		
		reader.close();
		return sprintList;
	}
	
	public List<BoardObject> jsonToBoard(StringReader object) {
		List<BoardObject> boards = new ArrayList<BoardObject>();
		
		JsonReader reader = Json.createReader(object);
		JsonArray array	  = reader.readObject().getJsonArray("values");
		BoardObject bo	  = null;
		//int id, String name, String type
		for(int i=0; i < array.size(); i++) {
			JsonObject obj = array.getJsonObject(i);
			bo = new BoardObject(obj.getInt("id"), obj.getString("name"), obj.getString("type"));
			boards.add(bo);
		}
		
		return boards;
	}
	
	public List<PriorityObject> jsonToPriority(StringReader object) {
		List<PriorityObject> priorities = new ArrayList<PriorityObject>();
		
		JsonReader reader = Json.createReader(object);
		JsonArray array = reader.readArray();
		PriorityObject po = null;
		
		for(int i=0; i<array.size();i++) {
			JsonObject obj = array.getJsonObject(i);
			po = new PriorityObject(obj.getString("name"), Integer.parseInt(obj.getString("id")));
			priorities.add(po);
		}
		
		return priorities;
	}

	public List<TaskObject> jsonToTaskType(StringReader object) {
		List<TaskObject> tasks = new ArrayList<TaskObject>();
		
		JsonReader reader = Json.createReader(object);
		JsonArray array = reader.readArray();
		TaskObject to = null;
		
		for(int i=0; i<array.size();i++) {
			JsonObject obj = array.getJsonObject(i);
			to = new TaskObject(obj.getString("name"), obj.getBoolean("subtask"));
			tasks.add(to);
		}
		
		return tasks;
	}
	
	public HashMap<String, CustomFieldObject> jsonToCFObj(StringReader object) {
		HashMap<String, CustomFieldObject> cfs = new HashMap<String, CustomFieldObject>();
		
		JsonReader reader = Json.createReader(object);
		JsonArray array = reader.readArray();
		CustomFieldObject cfo = null;
		
		for(int i=0; i<array.size();i++) {
			JsonObject obj = array.getJsonObject(i);
			if(obj.getBoolean("custom") == true) {
				cfo = new CustomFieldObject(obj.getString("name"), obj.getString("key"), obj.getJsonObject("schema").getInt("customId"));
				cfs.put(cfo.getName(), cfo);
			}
		}
		
		return cfs;
	}
	
	/* JSON Notes:
	 * 
	 * {} -- Object
	 * [] -- Array
	 * "" -- String
	 * 12 -- Int
	 * true/false -- boolean
	 * 
	 * JSON FILE FORMAT :
	 * {
	 * "key1": 12,
	 * "key2": "12",
	 * "key3": true,
	 * "array": [
	 * 		{
	 * 			"key-1": "
	 * 		},
	 * 		{ ... }
	 * ],
	 * "key4": 11
	 * }
	 */
	
}	
