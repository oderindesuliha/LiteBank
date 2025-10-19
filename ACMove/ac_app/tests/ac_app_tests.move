module ac_app::ac_app_tests {

    use std::unit_test::assert_eq;
    use ac_app::ac_app;

    #[test]
    fun test_ac_is_on(){
        let mut ac = ac_app::new(false);
        let turn_on_result = ac_app::turnOn(&mut ac);
        assert_eq!(turn_on_result, true);
        let state = ac_app::ac_state(&ac);
        assert_eq!(state, true);
    }

    #[test]
    fun test_ac_is_off(){
        let mut ac = ac_app::new(true);
        let turn_off_result = ac_app::turnOff(&mut ac);
        assert_eq!(turn_off_result, false);
        let state = ac_app::ac_state(&ac);
        assert_eq!(state, false);
    }

    #[test]
    fun test_increase_ac_temperature(){
        let mut ac = ac_app::new(true);

        ac_app::increase_temperature(&mut ac);
        ac_app::increase_temperature(&mut ac);
        ac_app::increase_temperature(&mut ac);
        ac_app::increase_temperature(&mut ac);

        let temp = ac_app::temperature(&ac);
        assert_eq!(temp, 28);

    }

#[test]
fun test_return_invalid_when_increasing_ac_temperature_when_off(){
    let mut ac = ac_app::new(false);
    let temp = ac_app::increase_temperature(&mut ac);
    assert_eq!(temp, b"Invalid Operation");
}

    #[test, expected_failure(abort_code = 1)]
    fun test_abort_when_increasing_ac_temperature_above_30(){
        let mut ac = ac_app::new(true);
        ac_app::increase_temperature(&mut ac); // 25
        ac_app::increase_temperature(&mut ac); // 26
        ac_app::increase_temperature(&mut ac); // 27
        ac_app::increase_temperature(&mut ac); // 28
        ac_app::increase_temperature(&mut ac); // 29
        ac_app::increase_temperature(&mut ac); // 30
        // next increase should abort because it would go above 30
    ac_app::increase_temperature(&mut ac); // aborts
    let temp = ac_app::temperature(&ac);
    assert_eq!(temp,30);
}

#[test]
    fun test_default_ac_temperature(){
        let ac = ac_app::new(true);
        let temp = ac_app::temperature(&ac);
        assert_eq!(temp, 24);
    }

#[test]
    fun test_turn_off_when_already_on(){
        let mut ac = ac_app::new(true);
        let turn_off_result = ac_app::turnOff(&mut ac);
        assert_eq!(turn_off_result, false);
        let state = ac_app::ac_state(&ac);
        assert_eq!(state, false);
    }

#[test]
    fun test_turn_on_when_already_off(){
        let mut ac = ac_app::new(false);
        let turn_on_result = ac_app::turnOn(&mut ac);
        assert_eq!(turn_on_result, true);
        let state = ac_app::ac_state(&ac);
        assert_eq!(state, true);
    }

#[test]
    fun test_decrease_ac_temperature(){
        let mut ac = ac_app::new(false);
         ac_app::decrease_temperature(&mut ac);

        let temp = ac_app::temperature(&ac);
        assert_eq!(temp, 23);
    }

    #[test, expected_failure(abort_code = 2)]
    fun test_abort_when_decreasing_ac_temperature_below_16(){
        let mut ac = ac_app::new(true);
        ac_app::decrease_temperature(&mut ac); // 23
        ac_app::decrease_temperature(&mut ac); // 22
        ac_app::decrease_temperature(&mut ac); // 21
        ac_app::decrease_temperature(&mut ac); // 20
        ac_app::decrease_temperature(&mut ac); // 19
        ac_app::decrease_temperature(&mut ac); // 18    
        ac_app::decrease_temperature(&mut ac); // 17
        // next decrease should abort because it would go below 16
        ac_app::decrease_temperature(&mut ac); // aborts
        let temp = ac_app::temperature(&ac);
        assert_eq!(temp,17);
    }
}