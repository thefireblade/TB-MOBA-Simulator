/**
 * Jason Huang
 * 110779373
 */
package com.example.tb_moba_simulator.objects;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.tb_moba_simulator.FirebaseManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.installations.remote.TokenResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * Object used for saving and loading a game
 */
public class SaveObject {
    private ArrayList<Map<String, Object>> defenses, mobs;
    private String landType, name, email = null;
    private Map<String, Object> playerInfo;
    private int turn;
    private ArrayList<Map<String, Object>> team_0;
    private ArrayList<Map<String, Object>>team_1;
    private String docID;
    private String log;
    private String date;

    /**
     * Constructor for a save object
     * @param defenses the defenses on the map
     * @param mobs The mobs on the map
     * @param landType The type of map
     * @param name The name of the game
     * @param playerInfo The information of the player
     * @param turn The turn count
     * @param team_0 A list of Characters that belong to team_0
     * @param team_1 A list of Characters that belong to team_1
     * @param date The date of when the game was saved
     * @param docID The document ID of save in the cloud
     * @param log The log of the game
     */
    public SaveObject(ArrayList<Map<String, Object>> defenses, ArrayList<Map<String, Object>> mobs,
                      String landType, String name, Map<String,Object> playerInfo,
                      int turn, ArrayList<Map<String, Object>> team_0, ArrayList<Map<String, Object>> team_1, String date, String docID, String log) {

        FirebaseManager.mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = FirebaseManager.mAuth.getCurrentUser();
        if(currentUser != null) {
            email = currentUser.getEmail();
        }
        this.defenses = defenses;
        this.mobs = mobs;
        this.landType = landType;
        this.name = name;
        this.playerInfo = playerInfo;
        this.turn = turn;
        this.team_0 = team_0;
        this.team_1 = team_1;
        this.date = date;
        this.docID = docID;
        this.log = log;
    }
    /** Getter and setter methods of the save object */
    public void setDefenses(ArrayList<Map<String, Object>> defenses) {
        this.defenses = defenses;
    }

    public ArrayList<Map<String, Object>> getMobs() {
        return mobs;
    }

    public void setMobs(ArrayList<Map<String, Object>> mobs) {
        this.mobs = mobs;
    }

    public Map<String, Object> getPlayerInfo() {
        return playerInfo;
    }

    public void setPlayerInfo(Map<String, Object> playerInfo) {
        this.playerInfo = playerInfo;
    }

    public ArrayList<Map<String, Object>> getTeam_0() {
        return team_0;
    }

    public void setTeam_0(ArrayList<Map<String, Object>> team_0) {
        this.team_0 = team_0;
    }

    public ArrayList<Map<String, Object>> getTeam_1() {
        return team_1;
    }

    public void setTeam_1(ArrayList<Map<String, Object>> team_1) {
        this.team_1 = team_1;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getDocID() {
        return docID;
    }

    public void setDocID(String docID) {
        this.docID = docID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Map<String, Object>> getDefenses() {
        return defenses;
    }

    public String getLandType() {
        return landType;
    }

    public void setLandType(String landType) {
        this.landType = landType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }


    public void setPlayerInfo(HashMap<String, Object> playerInfo) {
        this.playerInfo = playerInfo;
    }


    public void delete() {
        if (docID != null) {
            FirebaseManager.db.collection("saves").document(docID).delete();
            System.out.println("Delete Save initiated for : " + name);
            docID = null;
        }
    }
    public void insert() {
        if(this.email != null) {
            Map<String, Object> data = new HashMap<>();
            data.put("user", this.email);
            data.put("defenses", this.defenses);
            data.put("landType", this.landType);
            data.put("mobs", this.mobs);
            data.put("name", this.name);
            data.put("playerInfo", this.playerInfo);
            data.put("team_0", this.team_0);
            data.put("team_1", this.team_1);
            data.put("turn", this.turn);
            FirebaseManager.db.collection("saves").add(data)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            docID = documentReference.getId();
                        }
                    });
//                    .addOnFailureListener(new OnFailureListener() { Not collecting error data in this instance
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.w(TAG, "Error writing document", e);
//                        }
//                    });
        }
    }
}
