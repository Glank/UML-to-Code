package kirstein.source;

public abstract class CodeFactory {
	private SourceFileDistributionMethod sourceDistribution;
	
	public CodeFactory(SourceFileDistributionMethod sfdm){
		sourceDistribution = sfdm;
	}
	
	public CodeFactory(String extension){
		this(new OneClassPerFile(extension));
	}
	
	public SourceFileDistributionMethod getSourceDistribution() {
		return sourceDistribution;
	}
	
	public void setSourceDistribution(SourceFileDistributionMethod sfdm){
		sourceDistribution = sfdm;
	}
}
