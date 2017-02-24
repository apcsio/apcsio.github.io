package test.mesh;

import apcs.Window;

public class MeshDrawing {
	
	public static void main(String[] args) {
		
		String ip = "104.236.128.169";
		
		Window.mesh.join(ip);
		
		int minId = Window.mesh.read("id");
		int myId = minId + 1;
		Window.mesh.write("id", myId);
		
		int x = Window.rollDice(Window.width());
		int y = Window.rollDice(Window.height());
		
		Window.size(500, 500);
		Window.out.background("white");
		Window.out.color("black");
		
		while (true) {
			Window.mesh.write(myId + "x", x);
			Window.mesh.write(myId + "y", y);
			
			x += Window.rollDice(11) - 6;
			y += Window.rollDice(11) - 6;
			
			if (x < 0) x = 0;
			if (x > Window.width()) x = Window.width();
			if (y < 0) y = 0;
			if (y > Window.height()) y = Window.height();
			
			for (int i = 0 ; i <= Window.mesh.read("id") ; i++) {
				Window.out.circle(Window.mesh.read(i + "x"), Window.mesh.read(i + "y"), 10);
			}
			
			Window.sleep(33);
		}
	}
	
}
