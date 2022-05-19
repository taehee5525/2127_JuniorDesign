import React, { Component, useState, useEffect } from 'react';
import { ImageBackground, View, StyleSheet } from "react-native";
import { useNavigation } from '@react-navigation/native';

const SplashScreen = ({navigation}) => {

    useEffect(() => {
        const timer = setTimeout(()=>{
            const screen = 'First';
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