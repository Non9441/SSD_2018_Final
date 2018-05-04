package gameUI;

import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;

public class MyAnimTimer extends AnimationTimer {
	
	private ImageView curImg;
	private int curPos;
	private int steps;
	private boolean active;
	private String status = "";
	private int ssteps;
	
	
	public MyAnimTimer() {
		// TODO Auto-generated constructor stub
	}
	
	public MyAnimTimer(ImageView curImg, int curPos, int steps, String status) {
		this.curImg = curImg;
		this.curPos = curPos;
		this.steps = steps;
		this.status = status;
		active = false;
	}
	
	public void setUp(ImageView curImg, int curPos, int steps) {
		setImg(curImg);
		setcurPos(curPos);
		setSteps(steps);
		ssteps = 0;
		
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
		super.stop();
	}
	
	@Override
	public void handle(long now) {
		if(steps == 0) {
			if(status.equals("Snake") && ssteps != 0) {
				if (curPos % 20 == 1 || curPos % 20 == 11) {
					curImg.setTranslateY(curImg.getTranslateY() + 60);	
				} else if (curPos % 20 <= 10) {
					curImg.setTranslateX(curImg.getTranslateX() - 60);
				} else if (curPos % 20 >= 10) {
					curImg.setTranslateX(curImg.getTranslateX() + 60);
				}
				ssteps--;
				curPos--;
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				stop();
			}
		}
		if(steps > 0) {
				if (curPos % 20 == 0 || curPos % 20 == 10) {
					curImg.setTranslateY(curImg.getTranslateY() - 60);	
				} else if (curPos % 20 <= 10) {
					curImg.setTranslateX(curImg.getTranslateX() + 60);
				} else if (curPos % 20 >= 10) {
					curImg.setTranslateX(curImg.getTranslateX() - 60);
				}
				curPos++;
				steps--;
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		} 
		if (steps < 0){
			if (curPos % 20 == 1 || curPos % 20 == 11) {
				curImg.setTranslateY(curImg.getTranslateY() + 60);	
			} else if (curPos % 20 <= 10) {
				curImg.setTranslateX(curImg.getTranslateX() - 60);
			} else if (curPos % 20 >= 10) {
				curImg.setTranslateX(curImg.getTranslateX() + 60);
			}
			curPos--;
			steps++;
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}
