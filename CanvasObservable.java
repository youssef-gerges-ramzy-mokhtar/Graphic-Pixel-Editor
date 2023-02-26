// CanvasObservable is used to notify the LayersHander a.k.a the Canvas Observer when the Canvas is being resized
interface CanvasObservable {
	public void notifyCanvasObservers();
	public void addCanvasObserver(CanvasObserver observer);
	public void removeCanvasObserver(CanvasObserver observer);
}