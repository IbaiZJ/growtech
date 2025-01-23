package growtech.util.filtro;

public interface FiltroSelektorea<T,V> {
    public boolean filtroa(T balioa);
	public V selektorea(T balioa);
}
