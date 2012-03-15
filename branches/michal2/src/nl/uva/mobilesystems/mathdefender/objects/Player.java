package nl.uva.mobilesystems.mathdefender.objects;

import nl.uva.mobilesystems.mathdefender.GameModel;
import nl.uva.mobilesystems.mathdefender.GameSuperMarketModel;
import nl.uva.mobilesystems.mathdefender.gui.GUIConstants;
import nl.uva.mobilesystems.mathdefender.gui.TexMan;
import nl.uva.mobilesystems.mathdefender.physics.PhConstants;

import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.particle.SpriteParticleSystem;
import org.andengine.entity.particle.emitter.PointParticleEmitter;
import org.andengine.entity.particle.initializer.AccelerationParticleInitializer;
import org.andengine.entity.particle.initializer.BlendFunctionParticleInitializer;
import org.andengine.entity.particle.initializer.ColorParticleInitializer;
import org.andengine.entity.particle.initializer.RotationParticleInitializer;
import org.andengine.entity.particle.initializer.VelocityParticleInitializer;
import org.andengine.entity.particle.modifier.AlphaParticleModifier;
import org.andengine.entity.particle.modifier.ColorParticleModifier;
import org.andengine.entity.particle.modifier.ExpireParticleModifier;
import org.andengine.entity.particle.modifier.ScaleParticleModifier;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.graphics.PointF;
import android.opengl.GLES20;

/**
 * Class representing player's drone.
 * @author siemionides
 *
 */
public class Player extends AnimatedSprite{
	
	private GameModel model;
	
	private final PhysicsHandler mPhysicsHandler;
	private int myScore = 5;
    private Text myText;
    
	/*
	 * SETTERS & GETTERS ----------------------------------------------------
	 */
	public PhysicsHandler getPhysicsHanlder(){
		return this.mPhysicsHandler;
	}

	public int getScore() {
		return myScore;
	}

	// Setscore is meant to be used by SMLevel to set a budget for the player
		public void setScore(int budget) {
			myScore = budget;
		}
	
//	public void updateScore(int updateScore) {
//		myScore += updateScore;
//		//myText.setText(int.toString(myScore));
//	}
    
    /*
     * CONSTRUCTORS ------------------------------------------------------
     */

	public Player(final float pX, final float pY, final VertexBufferObjectManager pVertexBufferObjectManager, GameModel model){
		super(pX, pY, TexMan.getIt().mPlayerTextureRegion, pVertexBufferObjectManager);
		this.mPhysicsHandler = new PhysicsHandler(this);
		this.registerUpdateHandler(this.mPhysicsHandler);
		myText = new Text(20,20, TexMan.getIt().playerFont, "Score", "FPS: XXXXX".length(), pVertexBufferObjectManager);
		myText.setText(Integer.toString(myScore));
		this.attachChild(myText);
		this.model = model;
		
//		Particle System
//		{
//			final SpriteParticleSystem particleSystem = new SpriteParticleSystem(new PointParticleEmitter(0, 0), 6, 10, 200, TexMan.getIt().mParticlePlayerTextureRegion, pVertexBufferObjectManager);
//			particleSystem.addParticleInitializer(new BlendFunctionParticleInitializer<Sprite>(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE));
//			particleSystem.addParticleInitializer(new VelocityParticleInitializer<Sprite>(15, 22, -60, -90));
//			particleSystem.addParticleInitializer(new AccelerationParticleInitializer<Sprite>(5, 15));
//			particleSystem.addParticleInitializer(new RotationParticleInitializer<Sprite>(0.0f, 360.0f));
//			particleSystem.addParticleInitializer(new ColorParticleInitializer<Sprite>(1.0f, 0.0f, 0.0f));
//
//			particleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(0, 5, 0.5f, 2.0f));
//			particleSystem.addParticleModifier(new ExpireParticleModifier<Sprite>(11.5f));
//			particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(2.5f, 3.5f, 1.0f, 0.0f));
//			particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(3.5f, 4.5f, 0.0f, 1.0f));
//			particleSystem.addParticleModifier(new ColorParticleModifier<Sprite>(0.0f, 11.5f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f));
//			particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(4.5f, 11.5f, 1.0f, 0.0f));
//
//			this.attachChild(particleSystem);
//		}
	

//        this.mFont = new Font(this.mFontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32, true, Color.BLACK);
//		final ChangeableText elapsedText = new ChangeableText(100, 160, this.mFont, "Seconds elapsed:", "Seconds elapsed: XXXXX".length());
//		this.at
		
//		this.mPhysicsHandler.setVelocity(AndPhConstants.DEMO_VELOCITY, AndPhConstants.DEMO_VELOCITY);
		
	}
	 /*
     * OVERRIDEN METHOD ------------------------------------------------------
     */
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {

		//don't let the player go outside Camera's bounds!
		if((this.getX() <= 0 && this.mPhysicsHandler.getVelocityX() < 0) || 
				(this.getX() >= GUIConstants.CAMERA_WIDTH - this.getWidth() && this.mPhysicsHandler.getVelocityX() > 0))
			this.mPhysicsHandler.setVelocityX(0);
		else if((this.getY() <= 0 && this.mPhysicsHandler.getVelocityY() < 0) || 
				(this.getY() >= GUIConstants.CAMERA_HEIGHT - this.getHeight() && this.mPhysicsHandler.getVelocityY() > 0))
			this.mPhysicsHandler.setVelocityY(0);
		
		super.onManagedUpdate(pSecondsElapsed);
		
	}
	
	 /*
     * PUBLIC METHODS ------------------------------------------------------
     */
	
	/**
	 * Should be called when collision is detected.
	 * Currently it serves only collisions with enemies.
	 * @param enemy
	 */
	public void collisionDetected(Enemy enemy)
	{
		
		this.myScore += model instanceof GameSuperMarketModel ? - enemy.getResult(): enemy.getResult();
		this.model.engine.runOnUpdateThread(new Runnable() {
			
			public void run() {
				myText.setText(Integer.toString(myScore));
			}
		});
	}
	

	public void moveOnSwipe(){
		float moveByX = PhConstants.PLAYER_SWIPE_JUMP;
		if(this.getX() + moveByX > GUIConstants.CAMERA_WIDTH){
			moveByX = GUIConstants.CAMERA_WIDTH - this.getX() - this.getWidth();
		}
		this.setPosition(this.getX() + moveByX, this.getY());
	}
	

}
