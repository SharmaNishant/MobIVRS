package rj.ivr.dtmf;


public class DataBlock 
{
	private double[] block;

	public DataBlock(short[] buffer, int blockSize, int bufferReadSize)
	{
		block = new double[blockSize];
		
		for (int i = 0; i < blockSize && i < bufferReadSize; i++) {
			block[i] = (double) buffer[i];
		}
	}
	
	public DataBlock()
	{
		
	}
	
	public void setBlock(double[] block) 
	{
		this.block = block;
	}
	
	public double[] getBlock() 
	{
		return block;
	}
	
	public Spectrum FFT()
	{
		return new Spectrum(math.FFT.magnitudeSpectrum(block));
	}
}
