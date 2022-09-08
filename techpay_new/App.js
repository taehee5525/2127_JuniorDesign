import React, { Component, useEffect } from "react";
import { NavigationContainer } from "@react-navigation/native";
import { createStackNavigator } from "@react-navigation/stack";

import "react-native-gesture-handler";

import SplashScreen from './components/SplashScreen';
import FirstScreen from './components/FirstScreen';
import Main from './components/Main';
import SignIn from './components/SignIn';

// This is the stack used for navigating the screens
const Stack = createStackNavigator();
const App = () => {

    return ( // The order does not really matter
    // I added the screens in order i created them.
        <NavigationContainer>
            <Stack.Navigator screenOptions = {{ headerShown: false }}  initialRouterName = "SplashScreen">
                <Stack.Screen name = "SplashScreen" component = { SplashScreen } />
                <Stack.Screen name = "FirstScreen" component = { FirstScreen } />
                <Stack.Screen name = "Main" component = { Main } />
                <Stack.Screen name = "SignIn" component = { SignIn } />
            </Stack.Navigator>
        </NavigationContainer>
    )
}

export default App;