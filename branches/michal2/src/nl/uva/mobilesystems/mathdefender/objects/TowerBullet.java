package nl.uva.mobilesystems.mathdefender.objects;

import nl.uva.mobilesystems.mathdefender.andengine.events.EventsConstants;
import nl.uva.mobilesystems.mathdefender.andengine.events.ObjectPositionEvent;
import nl.uva.mobilesystems.mathdefender.andengine.events.ObjectPositionEventListener;
import nl.uva.mobilesystems.mathdefender.gui.TexMan;
import nl.uva.mobilesystems.mathdefender.physics.PhConstants;
import nl.uva.mobilesystems.mathdefender.utils.HelperClass;

import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;

/**
 * Class representing bullet that was shooted from Tower
 * @author siemionides
 *
 */
public class TowerBullet extends TiledSprite {

	
	
	private final PhysicsHandler mPhysicsHandler;
	
	private int typeOfBullet;

	/** Tower this bullet was shot from.*/
	private final Tower tower;
	
	private ObjectPositionEventListener listener; 

	

	/**
	 * Constructor
	 * @param shootVX X field of Vector of bullet's trajectory 
	 * @param shootVY Y field of Vector of bullet's trajectory
	 * @param tower
	 * @param pTiledTextureRegion
	 * @param pVertexBufferObjectManager
	 */
	public TowerBullet(float shootVX, float shootVY, Tower tower,
			ITiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(tower.getX(), tower.getY(), TexMan.getIt().mTowerBulletTextureRegion, pVertexBufferObjectManager);
		
		this.tower = tower;
		
		this.mPhysicsHandler = new PhysicsHandler(this);
		this.registerUpdateHandler(this.mPhysicsHandler);
		
		//standarize vector of Velocity, so the bullet will have pre-defined speed:
		
		float ratio = (float) Math.sqrt( shootVX*shootVX + shootVY*shootVY )/PhConstants.TOWER_BULLET_SPEED;
		Log.v("towerBullet",shootVX + "," + shootVY +" |stand:"+shootVX/ratio + "," +shootVY/ratio );
		this.mPhysicsHandler.setVelocity(shootVX/ratio, shootVY/ratio);
	}
	
	public Tower getTower() {
		return tower;
	}

	
	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		
			
//		Log.d("towerBullet2",  + this.getX() + " " + this.getY());
		if (HelperClass.isOutSideScene(this.getX(), this.getY())){
			fireEvent(EventsConstants.EVENT_OBJECT_BULLET_OUT_OF_SCENE);
		}
		super.onManagedUpdate(pSecondsElapsed);
	}

	public synchronized void addObjectPositionEventListener(ObjectPositionEventListener listener){
		this.listener = listener;
	}
	
	/**
	 * Should be called when bullet collides. It fires an event {@link EventsConstants#EVENT_OBJECT_BULLET_OUT_OF_SCENE}
	 * and notifies Tower about collision
	 */
	public void collisionDetected(){
		fireEvent(EventsConstants.EVENT_OBJECT_BULLET_OUT_OF_SCENE);
		this.tower.collisionBulletDetected();
	}
	
	
	public synchronized void removeObjectPositionEventListener(){
		this.listener = null;
	}
	
	private synchronized void fireEvent(int eventCode){
		ObjectPositionEvent event = new ObjectPositionEvent(this, eventCode);
		this.listener.handleObjectPositionEvent(event);
	}

	
}