package nl.uva.mobilesystems.mathdefender.game;

import java.util.LinkedList;

import nl.uva.mobilesystems.mathdefender.GameModel;
import nl.uva.mobilesystems.mathdefender.gui.TexMan;
import nl.uva.mobilesystems.mathdefender.objects.Enemy;
import nl.uva.mobilesystems.mathdefender.physics.PhConstants;

import org.andengine.entity.IEntity;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.graphics.Point;
import android.util.Log;

public class SuperMarketLevel extends Level {
	
	public int myBudget;
	private GameModel myModel;
	String[]array = { "one","two"};
	private LinkedList<String> demoSums = new LinkedList<String>();

	
	public SuperMarketLevel(int difficulty, int nrWaves, int nrTowers,
			Point screenDimensions,
			VertexBufferObjectManager objectManager,
			GameModel model, int budget)
	{
		super(difficulty, nrWaves, nrTowers, screenDimensions,
				objectManager, model);
		//Log.v("testingmarket", "SMLevel created with diff: " + difficulty + this.myDiff);
		this.myBudget = budget;
		this.myModel = model;
		model.getPlayer().setScore(budget);
		if (model.demo)
		{
			 for(String string : array)
                 demoSums.add(string);
		}
	}

	public void startNewWave()
	{
		//Log.v("testingmarket", "Waves left: " + this.wavesLeft);
		if(this.wavesLeft == 0)
		{
			this.model.nextLevel();
		}
		else
		{			
			this.wavesLeft --;
			LinkedList<AnimatedSprite>  tempEnemies = new LinkedList<AnimatedSprite>();
			
			
				for(int j=0; j< PhConstants.NR_ENEMIES_IN_WAVE; j++)
				{ //generating enemies
					//Log.v("testingmarket", "Creating Enemy with: " + this.myDiff);
					int x = this.model.screenDimensions.x; //the edge of a screen
					int y = (model.screenDimensions.y / (PhConstants.NR_ENEMIES_IN_WAVE+1) * (j+1)) - 10;	//so equal distribution on screen Width
					
					if (myModel.demo)
					{
						Enemy tempEnemy = new Enemy(x,y, this.model.objectManager, this.myDiff, this.model, TexMan.getIt().mSupermarketEnemyTextureregion);
						//tempEnemy.setSum(demoSums.getFirst());
						//demoSums.removeFirst();
						tempEnemy.addObjectPositionEventListener(this.model);
						tempEnemies.add(tempEnemy);
					}
					else
					{					
						Enemy tempEnemy = new Enemy(x,y, this.model.objectManager, this.myDiff, this.model, TexMan.getIt().mSupermarketEnemyTextureregion);
						tempEnemy.addObjectPositionEventListener(this.model);
						tempEnemies.add(tempEnemy);
					}
				}
				Wave tempWave = new Wave(tempEnemies);
				//Log.v("testingmarket", "new Wave created ");
				this.waves.offer(tempWave);
				
				this.setCurrentWave(this.getWaves().poll());
				for(IEntity object : this.getCurrentWave().getObjects())
				{
					this.model.addObjectToScene(object);
				}
			
		}
	}
}
