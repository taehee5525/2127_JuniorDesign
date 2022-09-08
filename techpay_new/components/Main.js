import React, { Component } from 'react';
import { AppBar, IconButton } from '@react-native-material/core';
import { useNavigation, useFocusEffect } from '@react-navigation/native';
import { StyleSheet, View, Text, BackHandler, Alert } from 'react-native';

const Main = () => {
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
    return (
        <>
            <AppBar titleStyle = {styles.titles}
                title = "My Account"
                color = "#F9F9F9"
                tintColor = "black"
                centerTitle = "True"
                />
        </>
    );
};

const styles = StyleSheet.create ( {
    titles: {
        color: "black",
        fontSize: 18,
        fontWeight: "bold",
        fontFamily: 'System'
    },

});

export default Main;