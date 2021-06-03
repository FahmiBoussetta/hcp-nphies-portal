import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IAddress } from 'app/shared/model/address.model';
import { getEntities as getAddresses } from 'app/entities/address/address.reducer';
import { getEntity, updateEntity, createEntity, reset } from './accident.reducer';
import { IAccident } from 'app/shared/model/accident.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IAccidentUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AccidentUpdate = (props: IAccidentUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { accidentEntity, addresses, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/accident');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getAddresses();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.date = convertDateTimeToServer(values.date);

    if (errors.length === 0) {
      const entity = {
        ...accidentEntity,
        ...values,
        location: addresses.find(it => it.id.toString() === values.locationId.toString()),
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
          <h2 id="hcpNphiesPortalApp.accident.home.createOrEditLabel" data-cy="AccidentCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.accident.home.createOrEditLabel">Create or edit a Accident</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : accidentEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="accident-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="accident-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="dateLabel" for="accident-date">
                  <Translate contentKey="hcpNphiesPortalApp.accident.date">Date</Translate>
                </Label>
                <AvInput
                  id="accident-date"
                  data-cy="date"
                  type="datetime-local"
                  className="form-control"
                  name="date"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.accidentEntity.date)}
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="typeLabel" for="accident-type">
                  <Translate contentKey="hcpNphiesPortalApp.accident.type">Type</Translate>
                </Label>
                <AvInput
                  id="accident-type"
                  data-cy="type"
                  type="select"
                  className="form-control"
                  name="type"
                  value={(!isNew && accidentEntity.type) || 'MVA'}
                >
                  <option value="MVA">{translate('hcpNphiesPortalApp.AccidentTypeEnum.MVA')}</option>
                  <option value="SCHOOL">{translate('hcpNphiesPortalApp.AccidentTypeEnum.SCHOOL')}</option>
                  <option value="SPT">{translate('hcpNphiesPortalApp.AccidentTypeEnum.SPT')}</option>
                  <option value="WPA">{translate('hcpNphiesPortalApp.AccidentTypeEnum.WPA')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="accident-location">
                  <Translate contentKey="hcpNphiesPortalApp.accident.location">Location</Translate>
                </Label>
                <AvInput id="accident-location" data-cy="location" type="select" className="form-control" name="locationId">
                  <option value="" key="0" />
                  {addresses
                    ? addresses.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/accident" replace color="info">
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
  addresses: storeState.address.entities,
  accidentEntity: storeState.accident.entity,
  loading: storeState.accident.loading,
  updating: storeState.accident.updating,
  updateSuccess: storeState.accident.updateSuccess,
});

const mapDispatchToProps = {
  getAddresses,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AccidentUpdate);
