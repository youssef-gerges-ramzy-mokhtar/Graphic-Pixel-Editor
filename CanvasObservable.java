interface CanvasObservable {
	public void notifyCanvasObservers();
	public void addCanvasObserver(CanvasObserver observer);
	public void removeCanvasObserver(CanvasObserver observer);
}