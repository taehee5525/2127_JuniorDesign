import React, { Component, useState, createRef} from 'react';
import { AppBar, Button, Stack} from "@react-native-material/core";
import { View, StyleSheet, Alert, Text, TextInput} from "react-native";
import { useNavigation } from '@react-navigation/native';

const Forgot = () => {
    const navigation = useNavigation();
    const [Email, setEmail] = useState('');

    const handleTPbutton = () => {
        const emailRegex = new RegExp("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$");

        if (!Email) {
            alert('Email is missing\nPlease check again');
            return;
        }

        if (!emailRegex.test(Email)) {
            alert('Email is not in correct format.\nPlease check again');
            return;
        }

        alert("The email you entered is :" + Email);
    }

    return  (
    <>
        <AppBar titleStyle = {styles.titles}
                title = "Password Reset"
                color = "#F9F9F9"
                tintColor = "black"
                centerTitle = "True"
        />
        <View style = {styles.container} >
            <Text style = {styles.centerText} >
                {"Forgot Password?"}
                </Text>
                <Text style = {styles.subTitle} >
                    {"Don't worry\n"}
                </Text>
                <Text style = {styles.subTitle} >
                    {"Here you can reset your password\n"}
                </Text>
        </View>

        <TextInput style= {styles.input}
            placeholder = "Email"
            keyboardType = "default"
            onChangeText = {(Email) => setEmail(Email)}
            />

        <Stack fill center spacing = { 20 }>
            <Button style = {styles.button}
                title = "Send Temporary Password"
                color = "#B3A369"
                tintColor = "white"
                 onPress = {handleTPbutton} />
             <Button style = {styles.button}
                 title = "Back to front page"
                 color = "#003057"
                 tintColor = "white"
                 onPress = {() => navigation.navigate('FirstScreen') } />
        </Stack>
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

    container: {
        alignItems: 'center',
    },

    centerText: {
        fontFamily: 'System',
        fontWeight: 'bold',
        color: 'black',
        fontSize: 40,
        fontStyle: 'italic',
        marginBottom: 10
    },

    input: {
        height: 40,
        margin: 10,
        borderBottomWidth: 0.5
    },

    subTitle: {
        fontFamily: 'System',
        color: 'gray',
        fontSize: 15,
        alignSelf: 'center'
    },

    button: {
        width: '80%'
    }


});


export default Forgot;