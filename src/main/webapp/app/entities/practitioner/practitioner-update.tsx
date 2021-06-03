import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './practitioner.reducer';
import { IPractitioner } from 'app/shared/model/practitioner.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPractitionerUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PractitionerUpdate = (props: IPractitionerUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { practitionerEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/practitioner');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...practitionerEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
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
            <AvForm model={isNew ? {} : practitionerEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="practitioner-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="practitioner-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="guidLabel" for="practitioner-guid">
                  <Translate contentKey="hcpNphiesPortalApp.practitioner.guid">Guid</Translate>
                </Label>
                <AvField id="practitioner-guid" data-cy="guid" type="text" name="guid" />
              </AvGroup>
              <AvGroup>
                <Label id="forceIdLabel" for="practitioner-forceId">
                  <Translate contentKey="hcpNphiesPortalApp.practitioner.forceId">Force Id</Translate>
                </Label>
                <AvField id="practitioner-forceId" data-cy="forceId" type="text" name="forceId" />
              </AvGroup>
              <AvGroup>
                <Label id="practitionerLicenseLabel" for="practitioner-practitionerLicense">
                  <Translate contentKey="hcpNphiesPortalApp.practitioner.practitionerLicense">Practitioner License</Translate>
                </Label>
                <AvField
                  id="practitioner-practitionerLicense"
                  data-cy="practitionerLicense"
                  type="text"
                  name="practitionerLicense"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="genderLabel" for="practitioner-gender">
                  <Translate contentKey="hcpNphiesPortalApp.practitioner.gender">Gender</Translate>
                </Label>
                <AvInput
                  id="practitioner-gender"
                  data-cy="gender"
                  type="select"
                  className="form-control"
                  name="gender"
                  value={(!isNew && practitionerEntity.gender) || 'Male'}
                >
                  <option value="Male">{translate('hcpNphiesPortalApp.AdministrativeGenderEnum.Male')}</option>
                  <option value="Female">{translate('hcpNphiesPortalApp.AdministrativeGenderEnum.Female')}</option>
                  <option value="Unknown">{translate('hcpNphiesPortalApp.AdministrativeGenderEnum.Unknown')}</option>
                  <option value="U">{translate('hcpNphiesPortalApp.AdministrativeGenderEnum.U')}</option>
                  <option value="N">{translate('hcpNphiesPortalApp.AdministrativeGenderEnum.N')}</option>
                  <option value="A">{translate('hcpNphiesPortalApp.AdministrativeGenderEnum.A')}</option>
                  <option value="B">{translate('hcpNphiesPortalApp.AdministrativeGenderEnum.B')}</option>
                  <option value="C">{translate('hcpNphiesPortalApp.AdministrativeGenderEnum.C')}</option>
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/practitioner" replace color="info">
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
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  practitionerEntity: storeState.practitioner.entity,
  loading: storeState.practitioner.loading,
  updating: storeState.practitioner.updating,
  updateSuccess: storeState.practitioner.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PractitionerUpdate);
