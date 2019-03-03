package scene;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JCheckBox;

import javazoom.jlgui.basicplayer.BasicPlayerException;
import module.DataManager;
import module.MoveThread;
import module.SceneManager;
import commandSet.ActorSet;
import MTool.MButton;
import MTool.MIO;
import MTool.MPanel;

public class ActorSelection extends MPanel {
	private static final long serialVersionUID = 1L;
	private MIO mio = new MIO();
	private SceneManager sm = new SceneManager();
	private DataManager data = new DataManager();
	private static int selectedActorNum;
	private JCheckBox jchkAI = new JCheckBox("Ê¹ÓÃAI");
	private Image titleImage;
	private ActorSet actorSet = new ActorSet();
	private MButton jbtNext = new MButton("next");
	private MButton jbtNextActor = new MButton("nextActor");
	private MButton jbtPrevActor = new MButton("previousActor");
	//private JLabel jlbPrompt = new JLabel();
	
	public ActorSelection() {
		super();
		titleImage = mio.readImage("System/title.png");
		
		jbtNext.setBounds(512, 32, 150, 50);
		add(jbtNext);
		
		/*jlbPrompt.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 28));
		jlbPrompt.setBounds(10, 10, 400, 32);
		jlbPrompt.setForeground(new Color(255,255,255,255));
		jlbPrompt.setText("¡¾ÇëÑ¡ÔñÍæ¼Ò1²Ù¿ØµÄ½ÇÉ«¡¿");
		add(jlbPrompt);
*/		
		//msgPrompt = new MessageLog(String.format("¡¾ÇëÍæ¼Ò%dÑ¡Ôñ½ÇÉ«¡¿",selectedActorNum), false);
		//add(msgPrompt);
		
		jbtNextActor.setBounds(20*32-32, 8*32, 64, 64);
		jbtPrevActor.setBounds(032, 8*32, 64, 64);
		add(jbtNextActor);
		add(jbtPrevActor);
		
		ButtonListener listener = new ButtonListener();
		jbtNextActor.addActionListener(listener);
		jbtPrevActor.addActionListener(listener);
		jbtNext.addActionListener(listener);
		add(actorSet);
		//setComponentZOrder(jbtNext, 0);
		
		jchkAI.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));
		jchkAI.setForeground(new Color(255,255,255,255));
		jchkAI.setOpaque(false);
		jchkAI.setBounds(512, 80, 128, 64);
		add(jchkAI);
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(titleImage, 0, 0,  22*32, 16*32, this);
	}
	
	class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == jbtNextActor) {
				actorSet.nextActor();
			} else if (e.getSource() == jbtPrevActor) {
				actorSet.previousActor();
			} else if (e.getSource() == jbtNext) {
				//System.out.println(selectedActorNum + " " + data.players().size());
				try {
					data.players().get(selectedActorNum).setAI(jchkAI.isSelected());
					data.players().get(selectedActorNum).setActor(actorSet.getShowingActor());
					data.players().get(selectedActorNum).getActor().init();
					selectedActorNum++;
					if (selectedActorNum < data.players().size()) {
						sm.switchScene(new ActorSelection());
					} else {
						data.players().init();
						sm.switchScene(new Map());
						sm.startGame();
					}
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (BasicPlayerException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
			}
		}
		
	}
}
