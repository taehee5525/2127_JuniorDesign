import React, { Component } from 'react';
import { AppBar, Button, Stack} from "@react-native-material/core";
import { ImageBackground, View, StyleSheet, Alert} from "react-native";
import { useNavigation } from '@react-navigation/native';

const FirstScreen = () => {
    const navigation = useNavigation();
    return  (
    <>
    <AppBar titleStyle = {styles.titles}
            title = "Welcome"
            color = "#F9F9F9"
            tintColor = "black"
            centerTitle = "True"
    />
    <View style = {styles.container}>
        <ImageBackground source = {require ('./images/bgimage.png')}
            resizeMode = "cover" style = {styles.image}>
        </ImageBackground>

    <Stack fill center spacing = { 15 }>
        <Button style = {styles.button}
            title = "Sign In"
            color = "#B3A369"
            tintColor = "white"/>
        <Button style = {styles.button}
            title = "Sign Up"
            color = "#003057"
            tintColor = "white"
            onPress = {() => navigation.navigate('SignUp') } />
    </Stack>
    </View>
    </>
    );


};

const styles = StyleSheet.create ( {
    container: {
        flex: 1,
    },
    image: {
        flex: 1,
        justifyContent: "center"
    },

    button: {
        width: "90%",
        padding: 5
    },

    titles: {
        color: "black",
        fontSize: 18,
        fontWeight: "bold",
        fontFamily: 'System'
    }
});


export default FirstScreen;