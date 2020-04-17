package com.example.memo.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Memos")
public class ListeDTO {

        @PrimaryKey(autoGenerate = true)
        public Long memoId;
        public String intitule;

        @Ignore
        public  ListeDTO(){}
        // Constructeur :
        public ListeDTO(String intitule)
        {
                this.intitule = intitule;
        }
}
