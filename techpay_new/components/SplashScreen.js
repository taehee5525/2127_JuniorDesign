import React, { Component, useState, useEffect } from 'react';
import { ImageBackground, View, StyleSheet } from "react-native";
import { useNavigation } from '@react-navigation/native';

const SplashScreen = ({navigation}) => {
    useEffect(() => {
        var screen = 'FirstScreen';
        const timer = setTimeout(()=>{
            navigation.navigate(screen);
        }, 1000);
    },[]);

    return (
        <ImageBackground source = {
            require('./images/splash_screen.png')}
            style = {{ width: '100%', height: '100%' }} />
    );
};

export default SplashScreen;