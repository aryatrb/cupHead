module com.example.fx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires com.google.gson;


    opens com.example.cupHead;
    opens com.example.cupHead.model;
    exports com.example.cupHead;
    exports com.example.cupHead.fxController;
    opens com.example.cupHead.fxController;
    opens com.example.assets.profilePictures;
    opens com.example.assets.bullet;
    opens com.example.assets.boss;
    opens com.example.assets.boss.bossFly;
    opens com.example.assets.boss.bossShoot;
    opens com.example.assets.miniBoss.purple;
    opens com.example.assets.miniBoss.yellow;
    opens com.example.assets.boss.phase2.shoot;
    opens com.example.assets.boss.phase2.fly;
    opens com.example.assets.backgrounds;
    opens com.example.assets.music;
    opens com.example.assets.healthBar;
    opens com.example.assets.cupHead;
}