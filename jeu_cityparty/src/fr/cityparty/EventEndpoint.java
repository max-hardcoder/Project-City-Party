package fr.cityparty;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.persistence.EntityNotFoundException;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;
import com.google.appengine.datanucleus.query.JDOCursorHelper;

@Api(name = "eventendpoint",
	version = "v1",
	clientIds = { "402784977642-jpudq4gu2tp4jti2kp3th30ob0sheb9q.apps.googleusercontent.com"})
public class EventEndpoint {

	
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listEvent")
	public CollectionResponse<Event> listEvent(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		PersistenceManager mgr = null;
		Cursor cursor = null;
		List<Event> execute = null;

		try {
			mgr = getPersistenceManager();
			Query query = mgr.newQuery(Event.class);
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				HashMap<String, Object> extensionMap = new HashMap<String, Object>();
				extensionMap.put(JDOCursorHelper.CURSOR_EXTENSION, cursor);
				query.setExtensions(extensionMap);
			}

			if (limit != null) {
				query.setRange(0, limit);
			}

			execute = (List<Event>) query.execute();
			cursor = JDOCursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Event obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Event> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}
	

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getEvent")
	public Event getEvent(@Named("id") Long id) {
		PersistenceManager mgr = getPersistenceManager();
		Event event = null;
		try {
			event = mgr.getObjectById(Event.class, id);
		} finally {
			mgr.close();
		}
		return event;
	}
	
	
	
	@ApiMethod(name = "registerUser")
	public CityUser registerUser(User user) throws OAuthRequestException, IOException {
		
		if ( user == null ) throw new OAuthRequestException("invalid user");
		
		  CityUser cityUser = new CityUser(user.getUserId(), user.getEmail());
	      PersistenceManager pm = getPersistenceManager();
	      pm.makePersistent(cityUser);
	      pm.close();
	      return cityUser;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param event the entity to be inserted.
	 * @return The inserted entity.
	 */
	

	
	@ApiMethod(name = "ajouterScore")
	public Score ajouterScore(User user,
			@Named("score") Long point ,
			@Named("event") Long event) throws OAuthRequestException, IOException {
				
		  if ( user == null ) throw new OAuthRequestException("invalid user");
		
	      PersistenceManager pm = getPersistenceManager();
	      
	      Score score = new Score(user.getUserId(), event, point);
	      
	      pm.makePersistent(score);
	      pm.close();
	     
	      return score;
	      
	  }
	
	
	
	
	
	
	@ApiMethod(name = "insertEvent")
	public Event insertEvent(Event event) /*throws OAuthRequestException, IOException*/ {
	  //  if (user != null) {
	    
		
	      PersistenceManager pm = getPersistenceManager();
	      event.players = new LinkedList<String>();
	      pm.makePersistent(event);
	      pm.close();
	      return event;
	  /*    return user;
	    } else {
	    	return user;
	    //  throw new OAuthRequestException("Invalid user.");
	    }*/
	  }
	  
	

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param event the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateEvent")
	public Event updateEvent(Event event) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			if (!containsEvent(event)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.makePersistent(event);
		} finally {
			mgr.close();

		}
		return event;
	}

	
	
	@ApiMethod(name = "ajoutPlayer")
	public Event ajoutPlayer(@Named("id") Long id, @Named("user") String userID) {
		
		   PersistenceManager pm = getPersistenceManager();
		   
		   Event event = getEvent(id);
		   
		   	if ( event.players == null) event.players = new LinkedList<String>();
		   	
		   	event.players.add(userID);
		   	
		      pm.makePersistent(event);
		      pm.close();
		      return event;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeEvent")
	public void removeEvent(@Named("id") Long id) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			Event event = mgr.getObjectById(Event.class, id);
			mgr.deletePersistent(event);
		} finally {
			mgr.close();
		}
	}

	
	
	private boolean containsEvent(Event event) {
		PersistenceManager mgr = getPersistenceManager();
		boolean contains = true;
		try {
			mgr.getObjectById(Event.class, event.getKey());
		} catch (javax.jdo.JDOObjectNotFoundException ex) {
			contains = false;
		} finally {
			mgr.close();
		}
		return contains;
	}

	private static PersistenceManager getPersistenceManager() {
		return PMF.get().getPersistenceManager();
	}

}
