package com.example.xavib.lego;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Part {



        private String color, tipus, id, imatge, quantitat;
        //private int quantitat;

        public Part(String id, String color, String tipus, String quantitat, String imatge) {
            this.id = id;
            this.color = color;
            this.tipus = tipus;
            this.quantitat = quantitat;
            this.imatge = imatge;
        }


        public String getId() { return id; }
        public void setId(String id) {this.id = id; }

        public String getColor() { return color; }
        public void setColor(String name) { this.color = color; }

        public String getTipus() {return tipus; }
        public void setTipus(String price ) { this.tipus = tipus; }

        public String getQuantitat() {   return quantitat;  }
        public void setQuantitat(String image) {   this.quantitat = quantitat;  }

        public String getImage() {   return imatge;  }
        public void setImage(String image) {   this.imatge = image;  }

    }




