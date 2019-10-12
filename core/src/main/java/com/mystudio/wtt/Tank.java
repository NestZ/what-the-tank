package com.mystudio.wtt;

import com.badlogic.gdx.Gdx;
import org.mini2Dx.core.graphics.Graphics;
import com.badlogic.gdx.graphics.Texture;
import org.mini2Dx.core.engine.geom.CollisionBox;
import org.mini2Dx.core.graphics.Sprite;

public class Tank extends Entity{
      private Key key;
      private final int TEAM;
      private final int MAX_HP;
      private final String COLOR;
      private boolean isDead;
      private boolean visible;
      private int hp;
      private CollisionBox collisionBox;
      private Sprite sprite;
      private int direction;
      private boolean isRValid;
      private boolean isLValid;
      private boolean isUValid;
      private boolean isDValid;
      private int id;
      private ClientRegister client;
      private float moveSpeed;

      public Tank(String color, float x, float y, int team, int id){
            this.TEAM = team;
            this.MAX_HP = 3;
            this.COLOR = color;
            this.moveSpeed = 2;
            this.hp = MAX_HP;
            this.id = id;
            this.isDead = false;
            this.visible = true;
            this.key = new Key();
            this.client = ClientRegister.getInstance();
            this.sprite = new Sprite(new Texture(Gdx.files.internal("tank.png")));
            this.collisionBox = new CollisionBox(x, y, this.sprite.getWidth(), this.sprite.getHeight());
      }

      public void update(float delta){
            if(this.visible){
                  this.collisionBox.preUpdate();
                  this.updateMove();
            }
      }

      public void interpolate(float alpha){
            if(this.visible)this.collisionBox.interpolate(null, alpha);
      }

      public void render(Graphics g){
            if(this.visible)g.drawSprite(this.sprite, this.collisionBox.getRenderX(), this.collisionBox.getRenderY());
      }

      public void setMove(int move){
            switch(move){
                  case 1 : 
                        this.key.setUp(true);break;
                  case 2 :
                        this.key.setDown(true);break;
                  case 3 :
                        this.key.setLeft(true);break;
                  case 4 :
                        this.key.setRight(true);break;
                  case 5 :
                        this.key.setUp(false);break;
                  case 6 :
                        this.key.setDown(false);break;
                  case 7 :
                        this.key.setLeft(false);break;
                  case 8 :
                        this.key.setRight(false);
            }
      }

      private void updateMove(){
            if(this.key.upKey && this.isUValid){
                  this.moveUp();
                  this.client.sendToServer("Update u");
            }
            if(this.key.downKey && this.isDValid){
                  this.moveDown();
                  this.client.sendToServer("Update d");
            }
            if(this.key.leftKey && this.isLValid){
                  this.moveLeft();
                  this.client.sendToServer("Update l");
            }
            if(this.key.rightKey && this.isRValid){
                  this.moveRight();
                  this.client.sendToServer("Update r");
            }
      }

      public void setValidMove(char c,boolean b){
            switch (c){
                  case 'R' :
                        this.isRValid = b;break;
                  case 'L' :
                        this.isLValid = b;break;
                  case 'U' :
                        this.isUValid = b;break;
                  case 'D' :
                        this.isDValid = b;break;
            }
      }

      public CollisionBox CollisionBox(){
            return this.collisionBox;
      }

      private void moveLeft(){
            this.collisionBox.set(this.collisionBox.getX() - this.moveSpeed, this.collisionBox.getY());
      }

      private void moveRight(){
            this.collisionBox.set(this.collisionBox.getX() + this.moveSpeed, this.collisionBox.getY());
      }

      private void moveUp(){
            this.collisionBox.set(this.collisionBox.getX(), this.collisionBox.getY() - this.moveSpeed);
      }

      private void moveDown(){
            this.collisionBox.set(this.collisionBox.getX(), this.collisionBox.getY() + this.moveSpeed);
      }

      public void setPos(float x, float y){
            this.collisionBox.set(x,y);
      }

      public float getX(){
            return this.collisionBox.getRenderX();
      }

      public float getY(){
            return this.collisionBox.getRenderY();
      }

      public float getWidth(){
            return this.sprite.getWidth();
      }

      public float getHeight(){
            return this.sprite.getHeight();
      }

      public int team(){
            return this.TEAM;
      }

      public boolean isDead(){
            return this.isDead;
      }
}