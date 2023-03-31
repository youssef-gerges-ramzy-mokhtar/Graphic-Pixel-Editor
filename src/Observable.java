public interface Observable {
	public void notifyObservers();
	public void addObserver(Observer observer);
	public void removeObserver(Observer observer);
}