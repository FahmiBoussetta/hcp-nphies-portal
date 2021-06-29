import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IPractitionerRole } from 'app/shared/model/practitioner-role.model';
import { getEntities as getPractitionerRoles } from 'app/entities/practitioner-role/practitioner-role.reducer';
import { getEntity, updateEntity, createEntity, reset } from './list-specialty-enum.reducer';
import { IListSpecialtyEnum } from 'app/shared/model/list-specialty-enum.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ListSpecialtyEnumUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const practitionerRoles = useAppSelector(state => state.practitionerRole.entities);
  const listSpecialtyEnumEntity = useAppSelector(state => state.listSpecialtyEnum.entity);
  const loading = useAppSelector(state => state.listSpecialtyEnum.loading);
  const updating = useAppSelector(state => state.listSpecialtyEnum.updating);
  const updateSuccess = useAppSelector(state => state.listSpecialtyEnum.updateSuccess);

  const handleClose = () => {
    props.history.push('/list-specialty-enum');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getPractitionerRoles({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...listSpecialtyEnumEntity,
      ...values,
      practitionerRole: practitionerRoles.find(it => it.id.toString() === values.practitionerRoleId.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...listSpecialtyEnumEntity,
          s: 'DOT01_00',
          practitionerRoleId: listSpecialtyEnumEntity?.practitionerRole?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hcpNphiesPortalApp.listSpecialtyEnum.home.createOrEditLabel" data-cy="ListSpecialtyEnumCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.listSpecialtyEnum.home.createOrEditLabel">
              Create or edit a ListSpecialtyEnum
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="list-specialty-enum-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.listSpecialtyEnum.s')}
                id="list-specialty-enum-s"
                name="s"
                data-cy="s"
                type="select"
              >
                <option value="DOT01_00">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT01_00')}</option>
                <option value="DOT01_01">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT01_01')}</option>
                <option value="DOT01_02">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT01_02')}</option>
                <option value="DOT01_03">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT01_03')}</option>
                <option value="DOT01_04">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT01_04')}</option>
                <option value="DOT01_05">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT01_05')}</option>
                <option value="DOT01_06">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT01_06')}</option>
                <option value="DOT01_07">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT01_07')}</option>
                <option value="DOT01_08">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT01_08')}</option>
                <option value="DOT02_00">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT02_00')}</option>
                <option value="DOT02_01">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT02_01')}</option>
                <option value="DOT03_00">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT03_00')}</option>
                <option value="DOT03_01">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT03_01')}</option>
                <option value="DOT03_02">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT03_02')}</option>
                <option value="DOT03_03">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT03_03')}</option>
                <option value="DOT04_00">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT04_00')}</option>
                <option value="DOT04_01">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT04_01')}</option>
                <option value="DOT04_02">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT04_02')}</option>
                <option value="DOT05_00">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT05_00')}</option>
                <option value="DOT05_01">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT05_01')}</option>
                <option value="DOT05_02">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT05_02')}</option>
                <option value="DOT05_03">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT05_03')}</option>
                <option value="DOT05_04">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT05_04')}</option>
                <option value="DOT05_05">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT05_05')}</option>
                <option value="DOT05_06">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT05_06')}</option>
                <option value="DOT05_07">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT05_07')}</option>
                <option value="DOT05_08">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT05_08')}</option>
                <option value="DOT05_09">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT05_09')}</option>
                <option value="DOT05_10">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT05_10')}</option>
                <option value="DOT06_00">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT06_00')}</option>
                <option value="DOT06_01">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT06_01')}</option>
                <option value="DOT06_02">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT06_02')}</option>
                <option value="DOT06_03">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT06_03')}</option>
                <option value="DOT06_04">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT06_04')}</option>
                <option value="DOT06_05">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT06_05')}</option>
                <option value="DOT07_00">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT07_00')}</option>
                <option value="DOT08_00">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT08_00')}</option>
                <option value="DOT08_01">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT08_01')}</option>
                <option value="DOT08_02">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT08_02')}</option>
                <option value="DOT08_03">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT08_03')}</option>
                <option value="DOT08_04">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT08_04')}</option>
                <option value="DOT08_05">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT08_05')}</option>
                <option value="DOT08_06">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT08_06')}</option>
                <option value="DOT08_07">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT08_07')}</option>
                <option value="DOT08_08">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT08_08')}</option>
                <option value="DOT08_09">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT08_09')}</option>
                <option value="DOT08_10">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT08_10')}</option>
                <option value="DOT08_11">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT08_11')}</option>
                <option value="DOT08_12">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT08_12')}</option>
                <option value="DOT08_13">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT08_13')}</option>
                <option value="DOT08_14">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT08_14')}</option>
                <option value="DOT08_15">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT08_15')}</option>
                <option value="DOT08_16">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT08_16')}</option>
                <option value="DOT08_17">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT08_17')}</option>
                <option value="DOT08_18">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT08_18')}</option>
                <option value="DOT08_19">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT08_19')}</option>
                <option value="DOT08_20">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT08_20')}</option>
                <option value="DOT08_21">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT08_21')}</option>
                <option value="DOT08_22">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT08_22')}</option>
                <option value="DOT08_23">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT08_23')}</option>
                <option value="DOT08_24">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT08_24')}</option>
                <option value="DOT08_25">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT08_25')}</option>
                <option value="DOT08_26">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT08_26')}</option>
                <option value="DOT09_00">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT09_00')}</option>
                <option value="DOT10_00">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT10_00')}</option>
                <option value="DOT10_01">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT10_01')}</option>
                <option value="DOT10_02">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT10_02')}</option>
                <option value="DOT10_03">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT10_03')}</option>
                <option value="DOT10_04">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT10_04')}</option>
                <option value="DOT10_05">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT10_05')}</option>
                <option value="DOT10_06">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT10_06')}</option>
                <option value="DOT10_07">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT10_07')}</option>
                <option value="DOT10_08">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT10_08')}</option>
                <option value="DOT10_09">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT10_09')}</option>
                <option value="DOT11_00">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT11_00')}</option>
                <option value="DOT11_01">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT11_01')}</option>
                <option value="DOT11_02">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT11_02')}</option>
                <option value="DOT11_03">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT11_03')}</option>
                <option value="DOT11_04">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT11_04')}</option>
                <option value="DOT11_05">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT11_05')}</option>
                <option value="DOT11_06">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT11_06')}</option>
                <option value="DOT11_07">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT11_07')}</option>
                <option value="DOT11_08">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT11_08')}</option>
                <option value="DOT11_09">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT11_09')}</option>
                <option value="DOT11_10">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT11_10')}</option>
                <option value="DOT11_11">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT11_11')}</option>
                <option value="DOT11_12">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT11_12')}</option>
                <option value="DOT11_13">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT11_13')}</option>
                <option value="DOT11_14">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT11_14')}</option>
                <option value="DOT11_15">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT11_15')}</option>
                <option value="DOT11_16">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT11_16')}</option>
                <option value="DOT12_00">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT12_00')}</option>
                <option value="DOT12_01">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT12_01')}</option>
                <option value="DOT12_02">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT12_02')}</option>
                <option value="DOT12_03">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT12_03')}</option>
                <option value="DOT12_04">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT12_04')}</option>
                <option value="DOT13_00">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT13_00')}</option>
                <option value="DOT13_01">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT13_01')}</option>
                <option value="DOT13_02">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT13_02')}</option>
                <option value="DOT13_03">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT13_03')}</option>
                <option value="DOT13_04">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT13_04')}</option>
                <option value="DOT13_05">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT13_05')}</option>
                <option value="DOT13_06">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT13_06')}</option>
                <option value="DOT13_07">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT13_07')}</option>
                <option value="DOT14_00">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT14_00')}</option>
                <option value="DOT14_01">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT14_01')}</option>
                <option value="DOT14_02">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT14_02')}</option>
                <option value="DOT14_03">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT14_03')}</option>
                <option value="DOT14_04">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT14_04')}</option>
                <option value="DOT14_05">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT14_05')}</option>
                <option value="DOT14_06">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT14_06')}</option>
                <option value="DOT14_07">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT14_07')}</option>
                <option value="DOT14_08">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT14_08')}</option>
                <option value="DOT14_09">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT14_09')}</option>
                <option value="DOT14_10">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT14_10')}</option>
                <option value="DOT14_11">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT14_11')}</option>
                <option value="DOT14_12">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT14_12')}</option>
                <option value="DOT14_13">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT14_13')}</option>
                <option value="DOT14_14">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT14_14')}</option>
                <option value="DOT14_15">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT14_15')}</option>
                <option value="DOT14_16">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT14_16')}</option>
                <option value="DOT14_17">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT14_17')}</option>
                <option value="DOT14_18">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT14_18')}</option>
                <option value="DOT14_19">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT14_19')}</option>
                <option value="DOT14_20">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT14_20')}</option>
                <option value="DOT15_00">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT15_00')}</option>
                <option value="DOT15_01">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT15_01')}</option>
                <option value="DOT15_02">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT15_02')}</option>
                <option value="DOT15_03">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT15_03')}</option>
                <option value="DOT15_04">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT15_04')}</option>
                <option value="DOT15_05">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT15_05')}</option>
                <option value="DOT15_06">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT15_06')}</option>
                <option value="DOT16_00">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT16_00')}</option>
                <option value="DOT16_01">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT16_01')}</option>
                <option value="DOT16_02">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT16_02')}</option>
                <option value="DOT17_00">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT17_00')}</option>
                <option value="DOT17_01">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT17_01')}</option>
                <option value="DOT17_02">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT17_02')}</option>
                <option value="DOT17_03">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT17_03')}</option>
                <option value="DOT17_04">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT17_04')}</option>
                <option value="DOT17_05">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT17_05')}</option>
                <option value="DOT17_06">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT17_06')}</option>
                <option value="DOT17_07">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT17_07')}</option>
                <option value="DOT17_08">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT17_08')}</option>
                <option value="DOT17_09">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT17_09')}</option>
                <option value="DOT17_10">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT17_10')}</option>
                <option value="DOT17_11">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT17_11')}</option>
                <option value="DOT17_12">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT17_12')}</option>
                <option value="DOT18_00">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT18_00')}</option>
                <option value="DOT18_01">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT18_01')}</option>
                <option value="DOT18_02">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT18_02')}</option>
                <option value="DOT18_03">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT18_03')}</option>
                <option value="DOT18_04">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT18_04')}</option>
                <option value="DOT18_05">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT18_05')}</option>
                <option value="DOT18_06">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT18_06')}</option>
                <option value="DOT18_07">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT18_07')}</option>
                <option value="DOT18_08">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT18_08')}</option>
                <option value="DOT18_09">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT18_09')}</option>
                <option value="DOT18_10">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT18_10')}</option>
                <option value="DOT18_11">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT18_11')}</option>
                <option value="DOT18_12">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT18_12')}</option>
                <option value="DOT19_00">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT19_00')}</option>
                <option value="DOT19_01">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT19_01')}</option>
                <option value="DOT19_02">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT19_02')}</option>
                <option value="DOT19_03">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT19_03')}</option>
                <option value="DOT19_04">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT19_04')}</option>
                <option value="DOT19_05">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT19_05')}</option>
                <option value="DOT19_06">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT19_06')}</option>
                <option value="DOT19_07">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT19_07')}</option>
                <option value="DOT19_08">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT19_08')}</option>
                <option value="DOT19_09">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT19_09')}</option>
                <option value="DOT19_10">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT19_10')}</option>
                <option value="DOT19_11">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT19_11')}</option>
                <option value="DOT19_12">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT19_12')}</option>
                <option value="DOT19_13">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT19_13')}</option>
                <option value="DOT19_14">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT19_14')}</option>
                <option value="DOT19_15">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT19_15')}</option>
                <option value="DOT19_16">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT19_16')}</option>
                <option value="DOT19_17">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT19_17')}</option>
                <option value="DOT19_18">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT19_18')}</option>
                <option value="DOT19_19">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT19_19')}</option>
                <option value="DOT19_20">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT19_20')}</option>
                <option value="DOT19_21">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT19_21')}</option>
                <option value="DOT19_22">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT19_22')}</option>
                <option value="DOT19_23">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT19_23')}</option>
                <option value="DOT19_24">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT19_24')}</option>
                <option value="DOT19_25">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT19_25')}</option>
                <option value="DOT19_26">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT19_26')}</option>
                <option value="DOT20_00">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT20_00')}</option>
                <option value="DOT20_01">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT20_01')}</option>
                <option value="DOT20_02">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT20_02')}</option>
                <option value="DOT20_03">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT20_03')}</option>
                <option value="DOT20_04">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT20_04')}</option>
                <option value="DOT20_05">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT20_05')}</option>
                <option value="DOT20_06">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT20_06')}</option>
                <option value="DOT21_00">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT21_00')}</option>
                <option value="DOT21_01">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT21_01')}</option>
                <option value="DOT21_02">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT21_02')}</option>
                <option value="DOT22_00">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT22_00')}</option>
                <option value="DOT22_01">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT22_01')}</option>
                <option value="DOT22_02">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT22_02')}</option>
                <option value="DOT22_03">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT22_03')}</option>
                <option value="DOT22_04">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT22_04')}</option>
                <option value="DOT22_05">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT22_05')}</option>
                <option value="DOT22_06">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT22_06')}</option>
                <option value="DOT22_07">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT22_07')}</option>
                <option value="DOT22_08">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT22_08')}</option>
                <option value="DOT23_00">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT23_00')}</option>
                <option value="DOT24_00">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT24_00')}</option>
                <option value="DOT25_00">{translate('hcpNphiesPortalApp.SpecialtyEnum.DOT25_00')}</option>
              </ValidatedField>
              <ValidatedField
                id="list-specialty-enum-practitionerRole"
                name="practitionerRoleId"
                data-cy="practitionerRole"
                label={translate('hcpNphiesPortalApp.listSpecialtyEnum.practitionerRole')}
                type="select"
              >
                <option value="" key="0" />
                {practitionerRoles
                  ? practitionerRoles.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/list-specialty-enum" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ListSpecialtyEnumUpdate;
