
# react-native-draw-overlay

## Getting started

`$ npm install react-native-draw-overlay --save`

### Mostly automatic installation

`$ react-native link react-native-draw-overlay`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-draw-overlay` and add `RNDrawOverlay.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNDrawOverlay.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNDrawOverlayPackage;` to the imports at the top of the file
  - Add `new RNDrawOverlayPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-draw-overlay'
  	project(':react-native-draw-overlay').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-draw-overlay/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-draw-overlay')
  	```

#### Windows
[Read it! :D](https://github.com/ReactWindows/react-native)

1. In Visual Studio add the `RNDrawOverlay.sln` in `node_modules/react-native-draw-overlay/windows/RNDrawOverlay.sln` folder to their solution, reference from their app.
2. Open up your `MainPage.cs` app
  - Add `using Draw.Overlay.RNDrawOverlay;` to the usings at the top of the file
  - Add `new RNDrawOverlayPackage()` to the `List<IReactPackage>` returned by the `Packages` method


## Usage
```javascript
import RNDrawOverlay from 'react-native-draw-overlay';

// TODO: What to do with the module?
RNDrawOverlay;
```
  