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
    opens com.example.cuphead.assets.profilepictures;
    opens com.example.cuphead.assets.bullet;
    opens com.example.cuphead.assets.boss;
    opens com.example.cuphead.assets.boss.bossFly;
    opens com.example.cuphead.assets.boss.bossShoot;
    opens com.example.cuphead.assets.miniboss.purple;
    opens com.example.cuphead.assets.miniboss.yellow;
    opens com.example.cuphead.assets.boss.phase2.shoot;
    opens com.example.cuphead.assets.boss.phase2.fly;
    opens com.example.cuphead.assets.backgrounds;
    opens com.example.cuphead.assets.music;
    opens com.example.cuphead.assets.healthbar;
    opens com.example.cuphead.assets.cuphead;
    opens com.example.cuphead.assets.boss.phase3.fly;
    opens com.example.cuphead.assets.boss.phase3.shoot;
    opens com.example.cuphead.assets.cuphead.moving.down;
    opens com.example.cuphead.assets.cuphead.moving.up;
    opens com.example.cuphead.assets.cuphead.moving.straight;
    opens com.example.cuphead.assets.cuphead.moving.turning.down;
    opens com.example.cuphead.assets.cuphead.moving.turning.up;
    opens com.example.cuphead.assets.boss.hitdust;
}