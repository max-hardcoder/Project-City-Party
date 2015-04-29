/* Copyright 2013 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.cityparty;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * Defines an entity representing the outcome of a game of Tic Tac Toe. This
 * class is used as both the definition of a score in the datastore, as well as
 * the format of the data sent over the wire.
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Score {
  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  public Long id;
  
  
  public Score( String user, Long game, Long score) {
	super();
	this.user = user;
	this.game = game;
	this.score = score;
}



@Persistent
  public String user;



@Persistent
  public Long score;

  
  @Persistent
  public Long game;



  public Long getId() {
    return id;
  }
  
 
}