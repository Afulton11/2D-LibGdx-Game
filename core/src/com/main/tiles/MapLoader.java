package com.main.tiles;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;

public class MapLoader  extends AsynchronousAssetLoader<Map, MapLoader.MapParameter>{
	
	private Map map;
	
	public MapLoader(FileHandleResolver resolver) {
		super(resolver);
	}
	

	@Override
	public void loadAsync(AssetManager manager, String fileName,
			FileHandle file, MapParameter mapParameter) {
		map = null;
		Json json = new Json();
		json.setOutputType(OutputType.minimal);
		map = json.fromJson(Map.class, file);
		
	}

	@Override
	public Map loadSync(AssetManager manager, String fileName, FileHandle file,
			MapParameter mapParameter) {
		Map map = this.map;
		this.map = null;
		return map;
	}

	@Override
	public Array<AssetDescriptor> getDependencies(String fileName,
			FileHandle file, MapParameter mapParameter) {
		return null;
	}
	
	static public class MapParameter extends AssetLoaderParameters<Map> {}
}
