package com.vorobiev.gameClass;

public class TextureRegion 
{
		public float u1, v1;
		public float u2, v2;
		public final Texture texture;
		private float width;
		private float height;
		public TextureRegion(Texture texture)
		{
			this.texture = texture;
		}
		public TextureRegion(Texture texture, float width, float height)
		{
			this.texture = texture;
			this.width = width;
			this.height = height;
		}
		public TextureRegion(Texture texture, float x, float y, float width, float height)
		{
			this.texture = texture;
			this.width = width;
			this.height = height;
			set(x, y);
		}
		public void set(float x, float y)
		{
			this.u1 = x / texture.width;
			this.v1 = y / texture.height;
			this.u2 = u1 + width / texture.width;
			this.v2 = v1 + height / texture.height;
		}
}
