import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICoverage } from 'app/shared/model/coverage.model';
import { getEntities as getCoverages } from 'app/entities/coverage/coverage.reducer';
import { getEntity, updateEntity, createEntity, reset } from './class-component.reducer';
import { IClassComponent } from 'app/shared/model/class-component.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IClassComponentUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ClassComponentUpdate = (props: IClassComponentUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { classComponentEntity, coverages, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/class-component');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getCoverages();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...classComponentEntity,
        ...values,
        coverage: coverages.find(it => it.id.toString() === values.coverageId.toString()),
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
          <h2 id="hcpNphiesPortalApp.classComponent.home.createOrEditLabel" data-cy="ClassComponentCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.classComponent.home.createOrEditLabel">Create or edit a ClassComponent</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : classComponentEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="class-component-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="class-component-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="typeLabel" for="class-component-type">
                  <Translate contentKey="hcpNphiesPortalApp.classComponent.type">Type</Translate>
                </Label>
                <AvInput
                  id="class-component-type"
                  data-cy="type"
                  type="select"
                  className="form-control"
                  name="type"
                  value={(!isNew && classComponentEntity.type) || 'Group'}
                >
                  <option value="Group">{translate('hcpNphiesPortalApp.ClassTypeEnum.Group')}</option>
                  <option value="Subgroup">{translate('hcpNphiesPortalApp.ClassTypeEnum.Subgroup')}</option>
                  <option value="Plan">{translate('hcpNphiesPortalApp.ClassTypeEnum.Plan')}</option>
                  <option value="Subplan">{translate('hcpNphiesPortalApp.ClassTypeEnum.Subplan')}</option>
                  <option value="Class">{translate('hcpNphiesPortalApp.ClassTypeEnum.Class')}</option>
                  <option value="Subclass">{translate('hcpNphiesPortalApp.ClassTypeEnum.Subclass')}</option>
                  <option value="Sequence">{translate('hcpNphiesPortalApp.ClassTypeEnum.Sequence')}</option>
                  <option value="Rxbin">{translate('hcpNphiesPortalApp.ClassTypeEnum.Rxbin')}</option>
                  <option value="Rxpcn">{translate('hcpNphiesPortalApp.ClassTypeEnum.Rxpcn')}</option>
                  <option value="Rxid">{translate('hcpNphiesPortalApp.ClassTypeEnum.Rxid')}</option>
                  <option value="Rxgroup">{translate('hcpNphiesPortalApp.ClassTypeEnum.Rxgroup')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="valueLabel" for="class-component-value">
                  <Translate contentKey="hcpNphiesPortalApp.classComponent.value">Value</Translate>
                </Label>
                <AvField
                  id="class-component-value"
                  data-cy="value"
                  type="text"
                  name="value"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="nameLabel" for="class-component-name">
                  <Translate contentKey="hcpNphiesPortalApp.classComponent.name">Name</Translate>
                </Label>
                <AvField id="class-component-name" data-cy="name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label for="class-component-coverage">
                  <Translate contentKey="hcpNphiesPortalApp.classComponent.coverage">Coverage</Translate>
                </Label>
                <AvInput id="class-component-coverage" data-cy="coverage" type="select" className="form-control" name="coverageId">
                  <option value="" key="0" />
                  {coverages
                    ? coverages.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/class-component" replace color="info">
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
  coverages: storeState.coverage.entities,
  classComponentEntity: storeState.classComponent.entity,
  loading: storeState.classComponent.loading,
  updating: storeState.classComponent.updating,
  updateSuccess: storeState.classComponent.updateSuccess,
});

const mapDispatchToProps = {
  getCoverages,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ClassComponentUpdate);
