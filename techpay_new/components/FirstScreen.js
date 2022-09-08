import React, { Component } from 'react';
import { AppBar, Button, Stack } from "@react-native-material/core";
import { ImageBackground, View, StyleSheet, Alert, BackHandler } from "react-native";
import { useNavigation, useFocusEffect } from '@react-navigation/native';

const FirstScreen = () => {
    const navigation = useNavigation();

    useFocusEffect(
        React.useCallback(() => {
            const onBackPress = () => {
                Alert.alert("Exit", "Do you want to exit the application?",
                    [
                        {
                            text: "Cancel",
                            onPress: () => null,
                            style: "cancel"
                        },
                        {
                            text: "Yes",
                            onPress: () => BackHandler.exitApp()
                        }
                    ]);
                return true;
            };

            BackHandler.addEventListener('hardwareBackPress', onBackPress);

            return () =>
                BackHandler.removeEventListener('hardwareBackPress', onBackPress);
            }, []),
        );

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
            tintColor = "white"
            onPress = {() => navigation.navigate('SignIn') }/>
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