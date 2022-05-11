import React from "react";
import { AppBar, Button, Stack} from "@react-native-material/core";

const App = () => (
<>


  <AppBar
    title="TechPay"
    color="black"
    tintColor="gold"
    centerTitle="True"
    />

    <Stack fill center spacing = {4}>

    <Button
        title ="Try me"
        loading = "true"
        />
    </Stack>





</>
);

export default App;