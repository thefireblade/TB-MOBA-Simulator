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

public class SaveObject {
    private ArrayList<HashMap> defenses, mobs;
    private String landType, name, email = null;
    private HashMap playerInfo;
    private int turn;
    private ArrayList<HashMap> team_0;
    private ArrayList<HashMap> team_1;
    private String docID = null;

    public SaveObject(ArrayList<HashMap> defenses, ArrayList<HashMap> mobs,
                      String landType, String name, HashMap playerInfo,
                      int turn, ArrayList<HashMap> team_0, ArrayList<HashMap> team_1) {

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
    }

    public ArrayList<HashMap> getDefenses() {
        return defenses;
    }

    public void setDefenses(ArrayList<HashMap> defenses) {
        this.defenses = defenses;
    }

    public ArrayList<HashMap> getMobs() {
        return mobs;
    }

    public void setMobs(ArrayList<HashMap> mobs) {
        this.mobs = mobs;
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

    public HashMap getPlayerInfo() {
        return playerInfo;
    }

    public void setPlayerInfo(HashMap playerInfo) {
        this.playerInfo = playerInfo;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public ArrayList<HashMap> getTeam_0() {
        return team_0;
    }

    public void setTeam_0(ArrayList<HashMap> team_0) {
        this.team_0 = team_0;
    }

    public ArrayList<HashMap> getTeam_1() {
        return team_1;
    }

    public void setTeam_1(ArrayList<HashMap> team_1) {
        this.team_1 = team_1;
    }

    public void delete() {
        if (docID != null) {
            FirebaseManager.db.collection("saves").document(docID).delete();
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
