interface ImageObservable {
	public void addObserver(ImageObserver observer);
	public void removeObserver(ImageObserver observer);
	public void notifyObservers();
}