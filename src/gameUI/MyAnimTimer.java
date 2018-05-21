package gameUI;

import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;

public class MyAnimTimer extends AnimationTimer {
	
	private ImageView curImg;
	private int curPos;
	private int steps;
	private boolean active;
	private String status = "";
	private int ssteps = 0;
	
	
	public MyAnimTimer() {
		
	}
	
	public MyAnimTimer(ImageView curImg, int curPos, int steps, int ssteps, String status) {
		this.curImg = curImg;
		this.curPos = curPos;
		this.steps = steps;
		this.ssteps = ssteps;
		this.status = status;
		active = false;
	}
	
	public void setUp(ImageView curImg, int curPos, int steps, int ssteps) {
		setImg(curImg);
		setcurPos(curPos);
		setSteps(steps);
		setSsteps(ssteps);
		
	}
	
	public void setSteps(int steps) {
		this.steps = steps;
	}
	
	public void setcurPos(int curPos) {
		this.curPos = curPos;
	}
	
	public void setSsteps(int ssteps) {
		this.ssteps = ssteps;
	}
	
	public void setImg(ImageView img) {
		curImg = img;
	}
	
	public int getSteps() {
		return steps;
	}
	
	public int getSSteps() {
		return ssteps;
	}
	
	public ImageView getImg() {
		return curImg;
	}
	
	public int getCurPos() {
		return curPos;
	}
	
	public String getStatus() {
		return status;
	}
	
	public boolean isActive() {
		return active;
	}
	
	@Override
	public void start() {
		active = true;
		super.start();
	}
	
	@Override
	public void stop() {
		active = false;
		notify();
		super.stop();
	}
	
	@Override
	public synchronized void handle(long now) {
		if(steps == 0) {
			if(status.equals("Snake")) {
				if(ssteps == 0) {
					this.stop();
				}
				if(ssteps < 0) {
					if (curPos % 20 == 1 || curPos % 20 == 11) {
						curImg.setTranslateY(curImg.getTranslateY() + 60);	
					}
					if (curPos % 20 <= 10 && curPos % 20 != 1 && curPos % 20 != 0) {
						curImg.setTranslateX(curImg.getTranslateX() - 60);
					}
					if ((curPos % 20 > 10 && curPos % 20 != 11) || curPos % 20 == 0) {
						curImg.setTranslateX(curImg.getTranslateX() + 60);
					}
					curPos--;
					ssteps++;
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} else {
				this.stop();
			}
		}
		if(steps > 0) {
				if (curPos % 20 == 0 || curPos % 20 == 10) {
					curImg.setTranslateY(curImg.getTranslateY() - 60);
				}
				if (curPos % 20 < 10 && curPos % 20 != 0) {
					curImg.setTranslateX(curImg.getTranslateX() + 60);
				}
				if (curPos % 20 > 10) {
					curImg.setTranslateX(curImg.getTranslateX() - 60);
				}
				curPos++;
				steps--;
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		} 
		if (steps < 0){
			if (curPos % 20 == 1 || curPos % 20 == 11) {
				curImg.setTranslateY(curImg.getTranslateY() + 60);	
			}
			if (curPos % 20 <= 10 && curPos % 20 != 1 && curPos % 20 != 0) {
				curImg.setTranslateX(curImg.getTranslateX() - 60);
			}
			if ((curPos % 20 > 10 && curPos % 20 != 11) || curPos % 20 == 0) {
				curImg.setTranslateX(curImg.getTranslateX() + 60);
			}
			curPos--;
			steps++;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
