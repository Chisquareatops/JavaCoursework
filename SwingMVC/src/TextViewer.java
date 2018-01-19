/**
 * A viewer class that implements View. When notified of changes in the connected DrawingModel, calls the toString method 
 * of the two highest level Shape objects to update the console with current information about the connected model.
 * Information is displayed about all Shape objects currently rendered in the connected model.
 * 
 * @author H Ertman
 */

public class TextViewer implements View {
	
	protected DrawingModel model; //the model to which this View is connected

	/**
	 * Updates the view to display text about all Shapes currently in the model. The coordinates are displayed in (x,y) format
	 * and the level is the level of the Shape in the connected model (representing the level of recursion). The color is
	 * displayed in RGB format. The type of Shape is displayed as a class name.
	 */
	@Override
	public void update() {
		for (int i=0; i<this.model.ShapeCollection.size(); i++) {
			System.out.println(this.model.ShapeCollection.get(i).toString());
		}
		System.out.println();
	}

	/**
	 * Connects this model to a view
	 * @param model the model to which this View is connected
	 */
	@Override
	public void connect(DrawingModel model) {
		this.model = model;
		
	}

}
