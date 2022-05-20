import React, { Component } from 'react';
import { AppBar } from "@react-native-material/core";

const Main = () => {
    return (
        <>
            <AppBar titleStyle = {styles.titles}
                title = "My Account"
                color = "#F9F9F9"
                tintColor = "black"
                centerTitle = "True" />
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