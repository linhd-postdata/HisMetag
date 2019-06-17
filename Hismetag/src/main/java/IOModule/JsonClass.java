/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IOModule;

/**
 *
 * @author mluisadiez
 */
public class JsonClass {
    
String term;
String position;
String word;
String type;
String mode;
String uri;
String current_term;
String ambiguous;
String another_meaning;
String another_meaning_uri;
String description;
String gazetteer;
String verification;
String geo;

public JsonClass(String term, String position, String word, String type, String mode, String uri,
			String current_term, String ambiguous, String another_meaning, String another_meaning_uri,
			String description, String gazetteer, String verification, String geo) {
		super();
		this.term = term;
		this.position = position;
		this.word = word;
		this.type = type;
		this.mode = mode;
		this.uri = uri;
		this.current_term = current_term;
		this.ambiguous = ambiguous;
		this.another_meaning = another_meaning;
		this.another_meaning_uri = another_meaning_uri;
		this.description = description;
		this.gazetteer = gazetteer;
		this.verification = verification;
		this.geo = geo;
	}
public String getTerm() {
	return term;
}
public void setTerm(String term) {
	this.term = term;
}
public String getPosition() {
	return position;
}
public void setPosition(String position) {
	this.position = position;
}
public String getWord() {
	return word;
}
public void setWord(String word) {
	this.word = word;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public String getMode() {
	return mode;
}
public void setMode(String mode) {
	this.mode = mode;
}
public String getUri() {
	return uri;
}
public void setUri(String uri) {
	this.uri = uri;
}
public String getCurrent_term() {
	return current_term;
}
public void setCurrent_term(String current_term) {
	this.current_term = current_term;
}
public String getAmbiguous() {
	return ambiguous;
}
public void setAmbiguous(String ambiguous) {
	this.ambiguous = ambiguous;
}
public String getAnother_meaning() {
	return another_meaning;
}
public void setAnother_meaning(String another_meaning) {
	this.another_meaning = another_meaning;
}
public String getAnother_meaning_uri() {
	return another_meaning_uri;
}
public void setAnother_meaning_uri(String another_meaning_uri) {
	this.another_meaning_uri = another_meaning_uri;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public String getGazetteer() {
	return gazetteer;
}
public void setGazetteer(String gazetteer) {
	this.gazetteer = gazetteer;
}
public String getVerification() {
	return verification;
}
public void setVerification(String verification) {
	this.verification = verification;
}
public String getGeo() {
	return geo;
}
public void setGeo(String geo) {
	this.geo = geo;
}
}
