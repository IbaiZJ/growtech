public class LocalTileFactory extends org.jxmapviewer.viewer.DefaultTileFactory {

    private final String localTilePath; // Ruta base de los tiles locales

    public LocalTileFactory(TileFactoryInfo info, String localTilePath) {
        super(info);
        this.localTilePath = localTilePath;
    }

    @Override
    public Tile getTile(int x, int y, int zoom) {
        // Construir la ruta del tile
        String tilePath = String.format("%s/%d/%d/%dpng.tile", localTilePath, zoom, x, y);
        File tileFile = new File(tilePath);

        if (tileFile.exists()) {
            return new Tile(tileFile.toURI().toString(), null);
        } else {
            // Retorna un tile vacío si no encuentra el archivo
            return super.getTile(x, y, zoom);
        }
    }
}