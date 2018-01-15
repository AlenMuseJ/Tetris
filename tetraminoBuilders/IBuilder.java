package tetraminoBuilders;

import java.awt.image.BufferedImage;

import facade.TetraminoBuilder;

public class IBuilder extends TetraminoBuilder{
	@Override
	public void setCoords() {
		int[][] coords = {
				{1, 1, 1, 1}
			};
		tetramino.setCoords(coords);
		int[][] reference = new int[coords.length][coords[0].length];		
		System.arraycopy(coords, 0, reference, 0, coords.length);
		tetramino.setReference(reference);
	}

	@Override
	public void setBlock() {
		// TODO Auto-generated method stub
		BufferedImage block = blocks.getSubimage(0, 0, blockSize, blockSize);
		tetramino.setBlock(block);
	}
	
	@Override
	public void setColor() {
		// TODO Auto-generated method stub
		tetramino.setColor(1);
	}
}
