module com.example.fx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires com.google.gson;


    opens com.example.cuphead;
    opens com.example.cuphead.model;
    exports com.example.cuphead;
    exports com.example.cuphead.fxcontroller;
    opens com.example.cuphead.fxcontroller;
    opens com.example.assets.profilepictures;
    opens com.example.assets.bullet;
    opens com.example.assets.boss;
    opens com.example.assets.boss.bossFly;
    opens com.example.assets.boss.bossShoot;
    opens com.example.assets.miniboss.purple;
    opens com.example.assets.miniboss.yellow;
    opens com.example.assets.boss.phase2.shoot;
    opens com.example.assets.boss.phase2.fly;
    opens com.example.assets.backgrounds;
    opens com.example.assets.music;
    opens com.example.assets.healthbar;
    opens com.example.assets.cuphead;
    opens com.example.assets.boss.phase3.fly;
    opens com.example.assets.boss.phase3.shoot;
}