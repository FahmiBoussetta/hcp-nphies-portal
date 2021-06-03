import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IClaimResponse } from 'app/shared/model/claim-response.model';
import { getEntities as getClaimResponses } from 'app/entities/claim-response/claim-response.reducer';
import { getEntity, updateEntity, createEntity, reset } from './total.reducer';
import { ITotal } from 'app/shared/model/total.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITotalUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TotalUpdate = (props: ITotalUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { totalEntity, claimResponses, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/total');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getClaimResponses();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...totalEntity,
        ...values,
        claimResponse: claimResponses.find(it => it.id.toString() === values.claimResponseId.toString()),
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
          <h2 id="hcpNphiesPortalApp.total.home.createOrEditLabel" data-cy="TotalCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.total.home.createOrEditLabel">Create or edit a Total</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : totalEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="total-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="total-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="categoryLabel" for="total-category">
                  <Translate contentKey="hcpNphiesPortalApp.total.category">Category</Translate>
                </Label>
                <AvField id="total-category" data-cy="category" type="text" name="category" />
              </AvGroup>
              <AvGroup>
                <Label id="amountLabel" for="total-amount">
                  <Translate contentKey="hcpNphiesPortalApp.total.amount">Amount</Translate>
                </Label>
                <AvField id="total-amount" data-cy="amount" type="string" className="form-control" name="amount" />
              </AvGroup>
              <AvGroup>
                <Label for="total-claimResponse">
                  <Translate contentKey="hcpNphiesPortalApp.total.claimResponse">Claim Response</Translate>
                </Label>
                <AvInput id="total-claimResponse" data-cy="claimResponse" type="select" className="form-control" name="claimResponseId">
                  <option value="" key="0" />
                  {claimResponses
                    ? claimResponses.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/total" replace color="info">
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
  claimResponses: storeState.claimResponse.entities,
  totalEntity: storeState.total.entity,
  loading: storeState.total.loading,
  updating: storeState.total.updating,
  updateSuccess: storeState.total.updateSuccess,
});

const mapDispatchToProps = {
  getClaimResponses,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TotalUpdate);
