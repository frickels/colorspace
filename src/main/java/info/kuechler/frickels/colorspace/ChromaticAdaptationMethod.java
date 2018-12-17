package info.kuechler.frickels.colorspace;

public class ChromaticAdaptationMethod {
    public static final ChromaticAdaptationMethod XYZ_SCALING = new ChromaticAdaptationMethod(new double[][] { //
            { 1., 0., 0. }, //
            { 0., 1., 0. }, //
            { 0., 0., 1. } //
    }, new double[][] { //
            { 1., 0., 0. }, //
            { 0., 1., 0. }, //
            { 0., 0., 1. } //
    });

    public static final ChromaticAdaptationMethod BRADFORD = new ChromaticAdaptationMethod(new double[][] { //
            { 0.8951, 0.2664, -0.1614 }, //
            { -0.7502, 1.7135, 0.0367 }, //
            { 0.0389, -0.0685, 1.0296 } //
    }, new double[][] { //
            { 0.9869929, -0.1470543, 0.1599627 }, //
            { 0.4323053, 0.5183603, 0.0492912 }, //
            { -0.0085287, 0.0400428, 0.9684867 } //
    });

    public static final ChromaticAdaptationMethod VON_KRIES = new ChromaticAdaptationMethod(new double[][] { //
            { 0.40024, 0.7076, -0.08081 }, //
            { -0.2263, 1.16532, 0.0457 }, //
            { 0., 0., 0.9182200 } //
    }, new double[][] { //
            { 1.8599364, -1.1293816, 0.2198974 }, //
            { 0.3611914, 0.6388125, -0.0000064 }, //
            { 0., 0., 1.0890636 } //
    });

    private final double[][] fdata;

    private final double[][] fdataReverse;

    public ChromaticAdaptationMethod(final double[][] data, final double[][] dataReverse) {
        this.fdata = data;
        this.fdataReverse = dataReverse;
    }

    public double[][] getData() {
        return fdata.clone();
    }

    public double[][] getDataReverse() {
        return fdataReverse.clone();
    }
}
