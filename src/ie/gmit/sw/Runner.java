package ie.gmit.sw;

public class Runner {
	public static void main(String[] args) {
		// Start new app window
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new AppWindow();
			}
		});
	}
}
