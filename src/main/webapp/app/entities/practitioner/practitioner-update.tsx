import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './practitioner.reducer';
import { IPractitioner } from 'app/shared/model/practitioner.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PractitionerUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const practitionerEntity = useAppSelector(state => state.practitioner.entity);
  const loading = useAppSelector(state => state.practitioner.loading);
  const updating = useAppSelector(state => state.practitioner.updating);
  const updateSuccess = useAppSelector(state => state.practitioner.updateSuccess);

  const handleClose = () => {
    props.history.push('/practitioner');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...practitionerEntity,
      ...values,
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
          ...practitionerEntity,
          gender: 'Male',
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hcpNphiesPortalApp.practitioner.home.createOrEditLabel" data-cy="PractitionerCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.practitioner.home.createOrEditLabel">Create or edit a Practitioner</Translate>
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
                  id="practitioner-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.practitioner.guid')}
                id="practitioner-guid"
                name="guid"
                data-cy="guid"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.practitioner.forceId')}
                id="practitioner-forceId"
                name="forceId"
                data-cy="forceId"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.practitioner.practitionerLicense')}
                id="practitioner-practitionerLicense"
                name="practitionerLicense"
                data-cy="practitionerLicense"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.practitioner.gender')}
                id="practitioner-gender"
                name="gender"
                data-cy="gender"
                type="select"
              >
                <option value="Male">{translate('hcpNphiesPortalApp.AdministrativeGenderEnum.Male')}</option>
                <option value="Female">{translate('hcpNphiesPortalApp.AdministrativeGenderEnum.Female')}</option>
                <option value="Unknown">{translate('hcpNphiesPortalApp.AdministrativeGenderEnum.Unknown')}</option>
                <option value="U">{translate('hcpNphiesPortalApp.AdministrativeGenderEnum.U')}</option>
                <option value="N">{translate('hcpNphiesPortalApp.AdministrativeGenderEnum.N')}</option>
                <option value="A">{translate('hcpNphiesPortalApp.AdministrativeGenderEnum.A')}</option>
                <option value="B">{translate('hcpNphiesPortalApp.AdministrativeGenderEnum.B')}</option>
                <option value="C">{translate('hcpNphiesPortalApp.AdministrativeGenderEnum.C')}</option>
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/practitioner" replace color="info">
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

export default PractitionerUpdate;
