package com.challenge.androidchallenge.Utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Ryan on 12/22/2015.
 */
public class SessionManager {
    //Shared preferences key for if the user is signed up
    private static final String SIGNEDUP_KEY = "isSignedUp";

    //Shared preferences key for the users email
    private static final String USER_EMAIL_KEY = "userEmail";

    //Shared Preferences
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context mContext;

    public SessionManager(Context context) {
        this.mContext = context;
        pref = PreferenceManager.getDefaultSharedPreferences(mContext);
        editor = pref.edit();
    }

    //Used to set if the user is signed up in the preferences file
    public void setSignedUp(boolean isSignedUp){
        editor.putBoolean(SIGNEDUP_KEY, isSignedUp);
        editor.commit();
    }

    //Used to check if the user is logged in or not
    public boolean isSignedUp() {
        return pref.getBoolean(SIGNEDUP_KEY, false);
    }

    //Used to set the user email in the preferences file
    public void setEmail(String email){
        editor.putString(USER_EMAIL_KEY, email);
        editor.commit();
    }

    //Used to get the user email in the preferences file
    public String getEmail(){
        return pref.getString(USER_EMAIL_KEY, "NO_EMAIL");
    }

    //Used to clear and remove the users preference file
    public void clearPreferenceFile(){
        editor.clear();
        editor.commit();
    }

    //Used to get the number of quests user has completed in the kingdom specified by passed in kingdomID
    public int getNumberOfCompletedQuests(String kingdomID){
        //builtKey format will be kingdom_{kingdomID}CompletedQuestsAmount
        String builtKey = "kingdom_";
        builtKey = builtKey.concat(kingdomID);
        builtKey = builtKey.concat("CompletedQuestsAmount");

        return pref.getInt(builtKey, 0);
    }

    //Used to add another quest completed to a kingdom specified by passed in kingdomID
    public void incrementCompletedQuestsAmount(String kingdomID){
        //Get the current amount of completed quests from the pref file
        int currentAmountOfCompletedQuests = getNumberOfCompletedQuests(kingdomID);

        //builtKey format will be kingdom_{kingdomID}CompletedQuestsAmount
        String builtKey = "kingdom_";
        builtKey = builtKey.concat(kingdomID);
        builtKey = builtKey.concat("CompletedQuestsAmount");

        editor.putInt(builtKey, (currentAmountOfCompletedQuests + 1));
        editor.commit();
    }


    //Used to set whether a quest has been completed or not
    public void setQuestCompleted(String questID, boolean completed){
        //builtKey format will be quest_{questID}Completed
        String builtKey = "quest_";
        builtKey = builtKey.concat(questID);
        builtKey = builtKey.concat("Completed");

        editor.putBoolean(builtKey, completed);
        editor.commit();
    }

    //Used to check whether a quest has been completed or not
    public boolean isQuestCompleted(String questID){
        //builtKey format will be quest_{questID}Completed
        String builtKey = "quest_";
        builtKey = builtKey.concat(questID);
        builtKey = builtKey.concat("Completed");

        return pref.getBoolean(builtKey, false);
    }
}
