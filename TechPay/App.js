import React, { Component, useEffect } from "react";
import { NavigationContainer} from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import { GoogleSignin } from '@react-native-google-signin/google-signin';

// These are the separate screens
import FirstScreen from './component/FirstScreen';
import SignUp from './component/SignUp';
import Terms from './component/Terms';
import SignIn from './component/SignIn';
import SplashScreen from './component/SplashScreen';
import Main from './component/Main';

const googleConf = () => {
    GoogleSignin.configure({
        webClientId:
        '269777593790-qi9sos475na9icfltb0528404i3mn0ou.apps.googleusercontent.com',
    })
}

const Stack = createStackNavigator();

const App = () => {
    useEffect(() => {
        googleConf();
    },[]);

    return (
        <NavigationContainer>
            <Stack.Navigator screenOptions = {{ headerShown: false }} initialRouterName = "SplashScreen" >
                <Stack.Screen name = "SplashScreen" component = { SplashScreen } />
                <Stack.Screen name = "First" component = { FirstScreen } />
                <Stack.Screen name = "SignUp" component = { SignUp } />
                <Stack.Screen name = "SignIn" component = { SignIn } />
                <Stack.Screen name = "Main" component = { Main } />
                <Stack.Screen name = "Terms" component = { Terms } />
            </Stack.Navigator>
        </NavigationContainer>
    )
}

export default App;