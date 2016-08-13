package Guildclash.Schematics;


	/*
	*
	*    This class is free software: you can redistribute it and/or modify
	*    it under the terms of the GNU General Public License as published by
	*    the Free Software Foundation, either version 3 of the License, or
	*    (at your option) any later version.
	*
	*    This class is distributed in the hope that it will be useful,
	*    but WITHOUT ANY WARRANTY; without even the implied warranty of
	*    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	*    GNU General Public License for more details.
	*
	*    You should have received a copy of the GNU General Public License
	*    along with this class.  If not, see <http://www.gnu.org/licenses/>.
	*
	*/
	 
	/**
	*
	* @author Max
	*/
	public class Schematic
	{
	 
	    private byte[] blocks;
	    private byte[] data;
	    private short width;
	    private short lenght;
	    private short height;
	 
	    public Schematic(byte[] blocks, byte[] data, short width, short lenght, short height)
	    {
	        this.blocks = blocks;
	        this.data = data;
	        this.width = width;
	        this.lenght = lenght;
	        this.height = height;
	    }
	 
	    /**
	    * @return the blocks
	    */
	    public byte[] getBlocks()
	    {
	        return blocks;
	    }
	 
	    /**
	    * @return the data
	    */
	    public byte[] getData()
	    {
	        return data;
	    }
	 
	    /**
	    * @return the width
	    */
	    public short getWidth()
	    {
	        return width;
	    }
	 
	    /**
	    * @return the lenght
	    */
	    public short getLenght()
	    {
	        return lenght;
	    }
	 
	    /**
	    * @return the height
	    */
	    public short getHeight()
	    {
	        return height;
	    }
	}

