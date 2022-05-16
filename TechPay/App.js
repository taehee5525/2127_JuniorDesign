import React from "react";
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';

// These are the separate screens
import FirstScreen from './FirstScreen';
import SignUp from './SignUp';
import Terms from './Terms';
import SignIn from './SignIn';

const Stack = createStackNavigator();

const App = () => {
    return (
        <NavigationContainer>
            <Stack.Navigator screenOptions = {{ headerShown: false }} initialRouterName = "Main" >
                <Stack.Screen name = "First" component = { FirstScreen } />
                <Stack.Screen name = "SignUp" component = { SignUp } />
                <Stack.Screen name = "SignIn" component = { SignIn } />
                <Stack.Screen name = "Terms" component = { Terms } />
            </Stack.Navigator>
        </NavigationContainer>
    )
}

export default App;